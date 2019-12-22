package com.yss.jvm.classloader;

public class JVMTest13 {
    public static void main(String[] args) throws Exception {
        MyClassLoader classLoader = new MyClassLoader("myloader");
        classLoader.setClasspath("/Users/yss/Desktop/");
        Class<?> aClass = classLoader.loadClass("com.yss.jvm.classloader.MySample");
        aClass.newInstance();


    }
}
