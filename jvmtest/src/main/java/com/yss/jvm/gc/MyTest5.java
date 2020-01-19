package com.yss.jvm.gc;

/*

启动参数：
-verbose:gc
-Xms20m
-Xmx20m
-Xmn10m
-XX:+PrintGCDetails
-XX:SurvivorRatio=8
-XX:+UseConcMarkSweepGC
 */
public class MyTest5 {

    public static void main(String[] args) {
        int size = 1024 * 1024;
        byte[] myAlloc1 = new byte[4* size];
        System.out.println("11111");
        byte[] myAlloc2 = new byte[4* size];
        System.out.println("22222");
        byte[] myAlloc3 = new byte[4* size];
        System.out.println("33333");
        byte[] myAlloc4 = new byte[2* size];

        System.out.println("44444");
    }
}
