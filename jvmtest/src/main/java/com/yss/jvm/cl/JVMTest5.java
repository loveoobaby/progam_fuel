package com.yss.jvm.cl;

import java.util.Random;

/**
 * 当一个接口初始化时，并不要求其父接口都完成初始化 ?
 *   在初始化一个类时，并不会先初始化它所实现的接口
 *   在初始化一个接口时，并不会先初始化它的父接口
 *   因此，一个父接口并不会因为它的子接口或者实现类的初始化而初始化，只有当程序首次使用特定接口的静态变量时，
 *   才会导致接口的初始化
 */
public class JVMTest5 {
    public static void main(String[] args) {
//        System.out.println(Child.b);
        System.out.println(Child5.c);
        System.out.println(Parent5.thread);
    }
}

interface Parent5 {
    // 接口的字段都是public static final类型
    public static final Thread thread = new Thread(){
        {
            System.out.println("Parent5 invoked");
        }
    };
}

class Child5 implements Parent5 {
    public static  int b  = 6 ;
    public static int c = new Random().nextInt(3);
}


