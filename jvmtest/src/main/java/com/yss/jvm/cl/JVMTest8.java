package com.yss.jvm.cl;

public class JVMTest8 {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("java.lang.String");
        System.out.println(clazz.getClassLoader());
        System.out.println(C.class.getClassLoader());
    }
}

class C {

}
