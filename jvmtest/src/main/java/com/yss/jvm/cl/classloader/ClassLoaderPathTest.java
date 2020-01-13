package com.yss.jvm.cl.classloader;

import java.util.stream.Stream;

/**
 * JVM自带了三种加载器，他们的类加载路径是是不同的
 *    1. 根类加载器查找路径是${JRE_HOME}/lib/rt.jar或者-Xbootclasspath选项指定
 *    2. 扩展类加载器加载路径是${JRE_HOME}/lib/ext/*.jar或者-Djava.ext.dirs指定的目录
 *    3. 系统类加载器加载CLASSPATH环境变量下的类
 *
 *  将自己写的类放入BootStrap类加载器的加载目录，根加载器就可以加载到
 *
 *
 */
public class ClassLoaderPathTest {
    public static void main(String[] args) {
        String bootClassPath = System.getProperty("sun.boot.class.path");
        Stream.of(bootClassPath.split(":")).forEach(System.out::println);
        System.out.println();
        System.out.println("--------");

        String extenClassPath = System.getProperty("java.ext.dirs");
        Stream.of(extenClassPath.split(":")).forEach(System.out::println);

        System.out.println();
        System.out.println("----AppClassPath----");
        Stream.of(System.getProperty("java.class.path").split(":")).forEach(System.out::println);
    }
}
