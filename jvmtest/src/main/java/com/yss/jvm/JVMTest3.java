package com.yss.jvm;

import java.util.UUID;

/**
 * 当常量可以在编译期确定的，就会写入调用类的常量池，不能确定的不会写入
 * Parent3.str虽然是常量，但在编译器无法确定，不会直接写入JVMTest3的常量池
 */

public class JVMTest3 {
    public static void main(String[] args) {
        System.out.println(Parent3.str);
    }
}

class Parent3 {
    public static final String str = UUID.randomUUID().toString();

    static {
        System.out.println("Parent3 static code");
    }
}
