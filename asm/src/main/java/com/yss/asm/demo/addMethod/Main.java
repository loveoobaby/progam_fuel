package com.yss.asm.demo.addMethod;

import com.yss.asm.demo.Utils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassReader reader = new ClassReader(TargetAddMethodClass.class.getName());
        ClassWriter writer = new ClassWriter(0);

        //新增加一个方法
        MethodVisitor mw = writer.visitMethod(ACC_PUBLIC + ACC_STATIC, "add", "([Ljava/lang/String;)V", null, null);
        mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mw.visitLdcInsn("this is add method print!");
        mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mw.visitInsn(RETURN);

        mw.visitEnd();

        reader.accept(writer, 0);

        byte[] code = writer.toByteArray();
        Utils.writeByte2File("./TargetAddMethodClass.class" ,code);

    }
}
