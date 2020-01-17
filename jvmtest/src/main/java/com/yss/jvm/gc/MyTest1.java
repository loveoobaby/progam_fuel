package com.yss.jvm.gc;

/**
 *  JVM参数：
 *  -verbose:gc
 *  -Xms20m  堆的初始大小
 *  -Xmx20m  堆的最大大小
 *  -Xmn10m  新生代的大小
 *  -XX:+PrintGCDetails 打印GC详情
 *  -XX:SurvivorRatio=8 Eden与Survivor的比例
 *
 *
 *  1. 新生代GC日志
 *   [GC (Allocation Failure) [PSYoungGen: 5939K->480K(9216K)] 5939K->4584K(19456K), 0.0039167 secs] [Times: user=0.01 sys=0.01, real=0.00 secs]
 *   [表示新生代GC及产生的原因] [使用的是Parallel Scavenge收集器，执行垃圾回收之前占据的空间->回收之后占据的空间（新生代总的容量）][回收之前堆总的存活对象->回收之后总的存活对象（总的堆的可用容量），GC时间]
 *
 *  2. Full GC日志
 *   [Full GC (Ergonomics) [PSYoungGen: 592K->0K(9216K)] [ParOldGen: 6152K->6560K(10240K)] 6744K->6560K(19456K), [Metaspace: 3185K->3185K(1056768K)], 0.0054964 secs] [Times: user=0.03 sys=0.01, real=0.01 secs]
 *
 *
 */
public class MyTest1 {

    public static void main(String[] args) {
        int size = 1024 * 1024;
        byte[] myAlloc1 = new byte[2* size];
        byte[] myAlloc2 = new byte[2* size];
        byte[] myAlloc3 = new byte[2* size];
        System.out.println("12");
        byte[] myAlloc4 = new byte[3* size];
//        byte[] myAlloc5 = new byte[3* size];
//        byte[] myAlloc6 = new byte[3* size];

        System.out.println("hello world");


    }

}
