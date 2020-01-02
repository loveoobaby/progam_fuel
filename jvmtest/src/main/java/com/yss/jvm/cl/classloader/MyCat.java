package com.yss.jvm.classloader;

public class MyCat {
    public MyCat(){
        System.out.println("mycat class loader is " + this.getClass().getClassLoader());
        System.out.println(""  + MySample.class.getClassLoader());
    }
}
