package com.yss.jvm.classloader;

import com.yss.jvm.classloader.MyClassLoader;

public class ClassUnloadTest {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new MyClassLoader("test");
        Class<?> clazz = classLoader.loadClass("com.yss.jvm.classloader.TestClassLoader");
        Object o = clazz.newInstance();
        System.out.println(o);
        classLoader = null;
        clazz = null;
        o = null;
        System.gc();


    }
}
