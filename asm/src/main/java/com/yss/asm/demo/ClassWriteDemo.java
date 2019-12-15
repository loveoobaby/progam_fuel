package com.yss.asm.demo;

import org.objectweb.asm.ClassWriter;

import java.io.*;

import static jdk.internal.org.objectweb.asm.Opcodes.V1_5;
import static org.objectweb.asm.Opcodes.*;

public class ClassWriteDemo {


    /**
     * package pkg;
     *
     * public interface Comparable extends Mesurable {
     *     int LESS = -1;
     *     int EQUAL = 0;
     *     int GREATER = 1;
     *
     *     int compareTo(Object var1);
     * }
     */

    public static void main(String[] args) {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "pkg/Comparable", null, "java/lang/Object",
                new String[]{"pkg/Mesurable"});
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd();
        byte[] b = cw.toByteArray();


        Utils.writeByte2File("./Comparable.class", b);
    }
}
