package com.yss.jvm;

/**
 * ClassLoader.loadClass方法并不会触发类的初始化，不是对类的主动使用
 * Class.forName会触发类的初始化，是对类的主动使用
 */
class CL {
    static {
        System.out.println("class cl static block");
    }
}
public class JVMTest11 {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        classLoader.loadClass("com.yss.jvm.CL");
        System.out.println("------------");
        Class.forName("com.yss.jvm.CL");
    }
}
