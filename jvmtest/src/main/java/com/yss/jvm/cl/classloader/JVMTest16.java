package com.yss.jvm.classloader;

/**
 *   1. 当前类加载器（current classLoader）
 *      每一个类都会使用自己的类加载器（即加载自身的类加载器）来去加载其他类（指的是所依赖的类）
 *      如果Class X引用了Class Y，那么Class X的加载器就会加载Class Y（前提是Class Y尚未被加载）
 *
 *   2. 线程的上下文加载器（ContextClassLoader）是从1.2开始引入的。
 *      如果没有通过setContextClassLoader进行设置的话，线程将继承其父线程的上下文类加载器。
 *      java应用运行时的初始线程的上下文类加载器是系统类加载器。在线程中运行的代码可以通过该类加载器来加载类与资源。
 *   3. 线程上下文加载器的重要性
 *      SPI（Service Provider Interface）
 *      父ClassLoader可以使用当前线程Thread.currentThread().getContextClassLoader()所指定的classLoader加载的类。
 *      这就改变了父ClassLoader不能使用子ClassLoader或是其他没有父子关系的ClassLoader加载类的情况，即改变了双亲委派模型。
 *
 *      线程上下文加载器就是当前线程的Current ClassLoader。
 *      在双亲委托模型下，类加载是由下而上的，即下层的类加载器会委托上层进行加载。但是对于SPI来说，有些接口是Java核心库所提供的，
 *      而java核心库是由启动类加载器来加载的，而这些接口的实现却是由不同的jar包（厂商提供），java的启动类加载器是不会加载其他
 *      来源的jar包。这样传统的双亲委托模型就无法满足SPI的要求，而通过给当前的线程设置上下文类加载器，就可以由设置的上下文类加载器
 *      来实现对于接口实现类的加载。
 *
 *   4. 线程上下文加载器一般使用模式（获取- 使用- 还原）
 *        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
 *        try{
 *             Thread.currentThread().setContextClassLoader(myClassLoader);
 *             myMethod();
 *             ...
 *        }finally {
 *            Thread.currentThread().setContextClassLoader(classLoader);
 *        }
 *
 *        myMethod()里面则调用了Thread.currentThread().getContextClassLoader()，获取当前线程上下文加载器做某些事情。
 *        如果一个类由类加载器A加载，那么这个类的依赖类也是由相同的类加载器加载的（如果该依赖类之前没有被加载过的话）
 *        ContextClassLoader的作用就是破坏Java的类加载委托机制。
 *
 *        当高层提供了统一的接口，而又要在高层加载（或实例化）低层的类时，就必须要通过线程的上下文加载器类帮助高层的ClassLoader找到
 *        并加载该类。
 *
 *
 */
public class JVMTest16 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(Thread.class.getClassLoader());
    }
}
