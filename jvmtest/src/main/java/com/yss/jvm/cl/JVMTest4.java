package com.yss.jvm;

/**
 * 对于对象的数组类型
 * 助记符：
 *    anewarray：创建一个引用类型（类、接口、数组）的数组，并将引用值压入栈顶
 *    newarray: 创建一个原始类型（如int，short、boolean、float、char类型）的数组，并将其引用值压入栈顶
 *
 */
public class JVMTest4 {
    public static void main(String[] args) {
        // 这行代码并不是对Parent4的主动使用，并不会导致Parent4类的初始化
        Parent4[] p4 = new Parent4[1];
        // 数组类是JVM在运行期动态生成的，表示成class [Lcom.yss.jvm.Parent4， 其父类就是Object
        System.out.println(p4.getClass());
        System.out.println(p4.getClass().getSuperclass());

        int[] a = new int[1];
        System.out.println(a.getClass());
        System.out.println(a.getClass().getSuperclass());

        char[] c = new char[3];
        System.out.println(c.getClass());

        boolean[] b = new boolean[4];
        System.out.println(b.getClass());
    }
}

class Parent4 {
    static {
        System.out.println("Parent4 static code");
    }
}
