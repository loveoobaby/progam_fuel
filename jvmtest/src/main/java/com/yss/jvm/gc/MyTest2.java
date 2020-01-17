package com.yss.jvm.gc;

/**
 * 1. -XX:+PrintCommandLineFlags: 打印JVM命令行参数
 * 2. -XX:+UseParallelGC: 新生代使用Parallel Scavenge收集器，老年代使用Parallel Old收集器
 * 3. -XX:PretenureSizeThreshold=4194304 : 超过该值的对象直接在老年代分配，但必须在GC使用-XX:+UseSerialGC才起作用
 *
 * 4. 一个对象的数据肯定分配在一段连续的空间，不存在一部分在新生代、一部分在老年代
 *
 */
public class MyTest2 {

    public static void main(String[] args) {
        int size = 1024*1024;
        byte[] myAlloc = new byte[5*size];

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
