package com.yss.asm.demo.staticfield;


/**
 * ldc： 将int、float、String从常量池中推送到栈顶
 * bipush: 将单字节（-128~127）的常量推送到栈顶
 * iconst_1 ~ iconst_5：将1~5推送至栈顶
 * sipush: 将一个整形（-32768~32767）推送到栈顶
 *
 */

public class JVMTest {
    public static void main(String[] args) {
//        System.out.println(Parent.str);
        System.out.println(6);
    }
}


class Parent{
    public static final String str = "hello world";
    public static final int s = 256;

    static {
        System.out.println("Parent static block");
    }
}

//class Child extends Parent {
//    static {
//        System.out.println("Child static block");
//    }
//}