package com.yss.jvm.cl;

/**
 * 使用子类对父类中的静态字段与静态方法的访问，是对父类的主动使用，不会触发子类的初始化
 */
class Parent10{
    public static int a = 3;
    static {
        System.out.println("Paren3 static blcok");
    }

    static void doString(){
        System.out.println("Parent do some thing");
    }
}

class Child10 extends Parent10 {
    static {
        System.out.println("Child static block");
    }
}

public class JVMTest10 {

    public static void main(String[] args) {
        System.out.println(Child10.a);
        Child10.doString();
    }
}
