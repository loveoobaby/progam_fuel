package com.yss.jvm.gc;


/*
启动参数：
-verbose:gc
-Xms200m
-Xmx200m
-Xmn50m
-XX:TargetSurvivorRatio=60
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-XX:+UseConcMarkSweepGC
-XX:+UseParNewGC
-XX:MaxTenuringThreshold=3
-XX:+PrintTenuringDistribution

 安全点：
    程序执行时并非在所有地方都能停顿下来进行GC，只有在到达安全点时才停顿。
    安全点的选定即不能太少以至于让GC等待时间太长，也不能过于频繁以至于过分增大运行时的负载。
    对于安全点，另一个需要考虑的问题是如何在GC发生时让所有线程都"跑"到最近的安全点上再停顿下来：抢占式中断和主动式中断。
       抢占式中断：它不需要线程的执行代码主动去配合，在GC发生时，首先把所有线程全部中断，如果有线程中断的地方不在安全点上，
                  就恢复线程，让它跑到安全点上
       主动式中断：当GC需要中断线程的时候，不直接对线程操作，仅仅简单设置一个标志，各个线程执行时主动去轮询这个标志，发现
                 中断标志位真时就自己中断挂起。轮询标记的地方与安全点是重合的，另外再加上创建对象需要分配内存的地方。


 */
public class MyTest4 {

    public static void main(String[] args) throws InterruptedException {
        byte[] byte1 = new byte[512*1024];
        byte[] byte2 = new byte[512*1024];

        myGc();
        Thread.sleep(1000);
        System.out.println("1111");

        myGc();
        Thread.sleep(1000);
        System.out.println("2222");

        myGc();
        Thread.sleep(1000);
        System.out.println("3333");

        myGc();
        Thread.sleep(1000);
        System.out.println("4444");

        byte[] mybyte3 = new byte[1024*1024];
        byte[] mybyte4 = new byte[1024*1024];
        byte[] mybyte5 = new byte[1024*1024];

        myGc();
        Thread.sleep(1000);
        System.out.println("5555");

        myGc();
        Thread.sleep(1000);
        System.out.println("6666");

    }

    private static void myGc(){
        for (int i = 0; i < 40; i++) {
            byte[] mybyte = new byte[1024*1024];
        }
    }
}
