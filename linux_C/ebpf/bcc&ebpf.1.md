### 1. eBPF与BCC
BPF Compiler Collection （BCC）是内核追踪与操作的工具集，包括一系列的工具和eBPF样例。eBPF首次在Linux内核3.15版本中加入的，很多BCC的工具需要Linux4.1及以上版本才可以。

### 2. Linux内核的升级
这里以Centos7.8为例升级内核   
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1.安装必要的依赖
```shell
yum update
yum install -y ncurses-devel make gcc bc bison flex elfutils-libelf-devel openssl-devel grub2
``` 
2. 配置内核  
   在内核源码目录执行如下shell：
```shell
cp -v /boot/config-`uname -r` .config
```  
    然后编辑.config文件, BPF部分的配置需设置成如下：
```
CONFIG_BPF=y
CONFIG_BPF_SYSCALL=y
# [optional, for tc filters]
CONFIG_NET_CLS_BPF=m
# [optional, for tc actions]
CONFIG_NET_ACT_BPF=m
CONFIG_BPF_JIT=y
# [for Linux kernel versions 4.1 through 4.6]
CONFIG_HAVE_BPF_JIT=y
# [for Linux kernel versions 4.7 and later]
CONFIG_HAVE_EBPF_JIT=y
# [optional, for kprobes]
CONFIG_BPF_EVENTS=y
```
3. 编译及安装内核
```shell
make -j8 && make modules_install && make install
reboot
```
<strong>注意：编译完成的内核源码不要删除，编译BPF字节码还需要这些代码。</strong>

### 3. 安装BCC
在centOS中安装比较简单
```shell
yum install bcc-tools
```
其他的

### 4. 运行BCC hellowold程序
```python
#!/usr/bin/python

from bcc import BPF

# This may not work for 4.17 on x64, you need replace kprobe__sys_clone with kprobe____x64_sys_clone
BPF(text='int kprobe__sys_clone(void *ctx) { bpf_trace_printk("Hello, World!\\n"); return 0; }').trace_print(fmt="{5}")
```

这段代码的作用是每当调用clone系统调用时，会打印hello world！
