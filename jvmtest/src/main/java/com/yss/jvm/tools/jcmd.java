package com.yss.jvm.tools;

/**
 * [root@ ~]# jcmd -help
 * Usage: jcmd <pid | main class> <command ...|PerfCounter.print|-f file>
 *    or: jcmd -l
 *    or: jcmd -h
 *
 *   command must be a valid jcmd command for the selected jvm.
 *   Use the command "help" to see which commands are available.
 *   If the pid is 0, commands will be sent to all Java processes.
 *   The main class argument will be used to match (either partially
 *   or fully) the class used to start Java.
 *   If no options are given, lists Java processes (same as -p).
 *
 *   PerfCounter.print display the counters exposed by this process
 *   -f  read and execute commands from the file
 *   -l  list JVM processes on the local machine
 *   -h  this help
 *
 *   1. jcmd <pid> VM.flags  打印JVM参数
 *   2. jcmd <pid> help  查询可用命令
 *   3. jcmd <pid> help JFR.dump 产看具体命令的可用选项
 *   4. jcmd <pid> PerfCounter.print 查看JVM性能相关的参数
 *   5. jcmd <pid> VM.uptime 查看JVM启动时间
 *   6. jcmd <pid> GC.class_histogram 查看JVM类的统计信息
 *   7. jcmd <pid> GC.heap_dump 导出转储文件
 *   8. jcmd <pid> VM.system_properties 查看JVM的系统属性
 *   9. jcmd <pid> VM.version 查看JVM的版本信息
 *   10. jcmd <pid> Thread.print 查看线程堆栈信息
 *   11.
 *
 *
 */

public class jcmd {
}
