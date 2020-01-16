package com.yss.jvm.tools;

/**
 *
 *  1. 统计堆的信息：jmap -heap
 *  2. 统计classloader的信息：jmap -clstats
 *  3. dump堆： jmap -dump:format=b
 *
 *
 * Usage:
 *     jmap [option] <pid>
 *         (to connect to running process)
 *     jmap [option] <executable <core>
 *         (to connect to a core file)
 *     jmap [option] [server_id@]<remote server IP or hostname>
 *         (to connect to remote debug server)
 *
 * where <option> is one of:
 *     <none>               to print same info as Solaris pmap
 *     -heap                to print java heap summary
 *     -histo[:live]        to print histogram of java object heap; if the "live"
 *                          suboption is specified, only count live objects
 *     -clstats             to print class loader statistics
 *     -finalizerinfo       to print information on objects awaiting finalization
 *     -dump:<dump-options> to dump java heap in hprof binary format
 *                          dump-options:
 *                            live         dump only live objects; if not specified,
 *                                         all objects in the heap are dumped.
 *                            format=b     binary format
 *                            file=<file>  dump heap to <file>
 *                          Example: jmap -dump:live,format=b,file=heap.bin <pid>
 *     -F                   force. Use with -dump:<dump-options> <pid> or -histo
 *                          to force a heap dump or histogram when <pid> does not
 *                          respond. The "live" suboption is not supported
 *                          in this mode.
 *     -h | -help           to print this help message
 *     -J<flag>             to pass <flag> directly to the runtime system
 */
public class jmap {
}
