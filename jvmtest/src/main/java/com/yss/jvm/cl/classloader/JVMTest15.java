package com.yss.jvm.cl.classloader;

import sun.misc.Launcher;

import java.util.stream.Stream;

/**
 *   在运行期，一个java类是由该类的完全限定名（binary name，二进制名）和用于加载该类的定义类加载器（define loader）所共同决定的。
 *   如果同样名称的类由不同的加载器所加载，那么这两个类就是不同的，即使是.class文件的字节码完全一样，并且从相同的位置加载也是如此。
 *
 *   实验一：
 *   如果用控制台来运行一下代码的话：java -cp . com.yss.jvm.classloader.JVMTest15
 *   系统类加载器路径与IDEA中是不同的
 *
 *   实验二：
 *   更改sun.boot.class.path属性：java -cp . -Dsun.boot.class.path=. com.yss.jvm.classloader.JVMTest15
 *
 *   1. 扩展类加载器与系统类加载器都是由根加载器加载的
 *
 *
 */
public class JVMTest15 {
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

        /**
         * 内建于JVM中的启动类加载器会加载java.lang.ClassLoader以及其他的Java平台类。
         * 当JVM启动时，一块特殊的机器码会运行，它会加载扩展类加载器与系统类加载器，
         * 这块特殊的机器码叫做启动类加载器
         *
         *  启动类加载器并不是java类，其他的加载器都是java类
         *  启动类加载器是特定于平台的机器指令，他负责开启整个加载过程。
         *
         *  其他加载器都被实现为java类。不过，总归要有一个组件来加载第一个java类加载器，从而让整个过程能够顺利进行下去，加载第一个
         *  纯java类加载器是启动类加载器的职责。
         *
         *  启动类加载器还会负责加载提供JRE正常运行所需的基本组件，这包括java.util与java.lang包中的类等。
         */
        System.out.println(ClassLoader.class.getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader().getClass().getClassLoader());

        // Launcher这个类是由启动类加载的，那么它的内部类也是启动类加载器加载的，包括扩展类加载器、系统类加载器
        System.out.println(Launcher.class.getClassLoader());

        // 系统类加载器是可以改变的，由java.system.class.loader属性指定，详见ClassLoader.getSystemClassLoader()的javaDoc
        System.out.println(ClassLoader.getSystemClassLoader());

    }
}
