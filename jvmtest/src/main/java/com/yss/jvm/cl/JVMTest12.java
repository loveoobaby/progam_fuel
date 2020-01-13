package com.yss.jvm.cl;

import java.net.URL;

/**
 * JVM自带的加载器有三个：
 *      应用类加载器AppClassLoader
 *      扩展类加载器ExtClassLoader
 *      根加载器 用null表示
 *
 *  数组类型的Class是在运行时动态生成的，并不是由ClassLoader加载的。
 *  数组类调用Class.getClassLoader()返回的结果与数组的元素类型相同。
 *  如果数组元素是原始类型，它是没有ClassLoader的
 */
public class JVMTest12 {
    public static void main(String[] args) {
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        System.out.println(cl);
        while (cl != null){
            cl = cl.getParent();
            System.out.println(cl);
        }

        //上下文类加载器
        ClassLoader context = Thread.currentThread().getContextClassLoader();
        System.out.println(context);

        String resource = "com/yss/jvm/JVMTest12.class";
        URL r = context.getResource(resource);
        System.out.println(r);

        String[] strs = new String[10];
        System.out.println(strs.getClass().getClassLoader());

        JVMTest12[] test2 = new JVMTest12[1];
        System.out.println(test2.getClass().getClassLoader());

        int[] a = new int[1];
        System.out.println(a.getClass().getClassLoader());

    }
}
