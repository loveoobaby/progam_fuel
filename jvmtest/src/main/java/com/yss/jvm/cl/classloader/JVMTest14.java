package com.yss.jvm.classloader;

import java.lang.reflect.Method;

public class JVMTest14 {
    public static void main(String[] args) throws Exception {
        MyClassLoader loader1 = new MyClassLoader("loader1");
        MyClassLoader loader2 = new MyClassLoader("loader2");
        loader1.setClasspath("/Users/yss/Desktop/");
        loader2.setClasspath("/Users/yss/Desktop/");

        Class<?> class1 = loader1.loadClass("com.yss.jvm.classloader.MyPerson");
        Class<?> class2 = loader2.loadClass("com.yss.jvm.classloader.MyPerson");

        System.out.println(class1 == class2);

        Object o1 = class1.newInstance();
        Object o2 = class2.newInstance();
        Method method = class1.getMethod("setPerson", Object.class);
        method.invoke(o1, o2);

    }
}
