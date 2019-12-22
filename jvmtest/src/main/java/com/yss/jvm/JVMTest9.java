package com.yss.jvm;

import java.util.Random;

class FinalTest {
    public static final int i = 2;
    public static final int j = new Random().nextInt();

    static {
        System.out.println("Final test ");
    }
}

public class JVMTest9 {
    public static void main(String[] args) {
        System.out.println(FinalTest.i);
        System.out.println(FinalTest.j);
    }
}
