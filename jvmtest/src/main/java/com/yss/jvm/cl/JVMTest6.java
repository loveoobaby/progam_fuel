package com.yss.jvm.cl;

/**
 * 连接阶段中的准备阶段是给类的静态变量赋予默认值，
 * 初始化阶段会按照代码自上而下的顺序执行，将各个静态变量从默认值变为实际的值
 * java编译器会为类的初始化生成一个方法"<clinit>"
 *
 * 类的实例化：
 *   为新的对象分配内存
 *   为实例变量赋予默认值
 *   为实例变量赋予正确的初始值
 *   java编译器为它编译的每一个类都至少生成一个实例初始化方法"<init>"
 *
 * 类加载器：
 *   1. JVM自带的加载器
 *       Bootstrap加载器
 *       Extension加载器
 *       System加载器
 *   2. 用户自定义加载器
 *       java.lang.ClassLoader的子类
 */
public class JVMTest6 {
    public static void main(String[] args) {
//        Singleton singleton = Singleton.getInstance();
        System.out.println(Singleton.count1);
        System.out.println(Singleton.count2);
    }


}

class Singleton{
    public static int count1 = 1;
    private static Singleton singleton = new Singleton();

    private Singleton(){
        count1++;
        count2++;
    }

    public static int count2 = 0;


    public static Singleton getInstance(){
        return singleton;
    }
}
