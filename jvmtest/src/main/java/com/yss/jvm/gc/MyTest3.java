package com.yss.jvm.gc;
/*
   -verbose:gc
   -Xms20m
   -Xmx20m
   -Xmn10m
   -XX:+PrintGCDetails
   -XX:SurvivorRatio=8
   -XX:+PrintCommandLineFlags
   -XX:MaxTenuringThreshold=5  在可以自动调节对象晋升到老年代GC中，设置该阈值的最大值。该参数默认值是15，CMS默认是6，G1默认是15.
   -XX:+PrintTenuringDistribution

   经历多次GC后，存活对象会在From 与to Survivor之间来回存放，而这里面有一个重要的前提是这两个空间由足够的大小来存放这些对象。在GC算法中，
   会计算每个对象的年龄，如果达到某个年龄后总大小已经大于Survivor空间的50%，那么这时候就需要调整阈值。，不能等到默认的15次GC后才晋升，因为这样
   会导致Survivor空间不足，所以需要调整阈值，让这些对象尽快完成晋升。

    空间分配担保：
      在发生Minor GC之前，虚拟机会先检查老年代最大可用的连续空间是否大于新生代所有对象总空间，如果这个条件成立，那么Minor GC可以确保安全的。
      当大量对象在MinorGC后仍然存活，就需要老年代进行空间分配担保，把Survivor无法容纳的对象直接进入老年代。如果老年代判断到剩余空间不足（根据
      以往每一次回收晋升到老年代对象容量的平均值作为经验值），则进行一次FullGC

 */
public class MyTest3 {
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
