package com.yss.jvm.classloader;

public class MySample {
    public MySample(){
        System.out.println("mysample class loader is " + MySample.class.getClassLoader());
        // 这里会加载MyCat这个类，是由加载了MySample的类加载器加载的，
        // 如果MySample由App加载器加载，而MyCat必须由App加载器的子类加载，那么会出异常
        new MyCat();
    }
}
