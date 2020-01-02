package com.yss.asm.demo.classreader;

import org.objectweb.asm.*;

import java.io.IOException;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

public class ClassPrinter extends ClassVisitor {

    public ClassPrinter() {
        super(ASM5);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println("version = " + version);
        System.out.println("class name = " + name);
        System.out.println("signature = " + signature);

        for (String face : interfaces){
            System.out.println(" == "+ face);
        }

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println(name);
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        System.out.println(name);
//        return super.visitField(access, name, descriptor, signature, value);
        return null;
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        System.out.println(attribute);
    }

    @Override
    public void visitSource(String source, String debug) {
        System.out.println(source);
    }

    public static void main(String[] args) throws IOException {
        ClassReader reader = new ClassReader("java.lang.String");
        ClassPrinter printer = new ClassPrinter();
        reader.accept(printer, 0);
    }
}
