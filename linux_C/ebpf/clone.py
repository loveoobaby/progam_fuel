from bcc import BPF

# define BPF program
prog = """
int hello(void *ctx) {
    bpf_trace_printk("my bcc program\\n");
    return 0;
}
"""

# load BPF program
b = BPF(text=prog)

print(b.get_syscall_fnname("sync"))

b.attach_kprobe(event=b.get_syscall_fnname("sync"), fn_name="hello")

# header
print("%-18s %-16s %-6s %s" % ("TIME(s)", "COMM", "PID", "MESSAGE"))

# format output
while 1:
    try:
        (task, pid, cpu, flags, ts, msg) = b.trace_fields()
    except ValueError:
        continue
    print("%-18.9f %-16s %-6d %s" % (ts, task, pid, msg))
