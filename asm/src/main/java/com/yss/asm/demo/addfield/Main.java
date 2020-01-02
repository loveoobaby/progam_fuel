package com.yss.asm.demo.addfield;

import com.yss.asm.demo.Utils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class Main {
    public static void main(String[] args) throws IOException {

        ClassReader reader = new ClassReader("com.yss.asm.demo.addfield.TargetAddFiledClass");
        ClassWriter writer = new ClassWriter(0);
        AddFieldAdapter adapter = new AddFieldAdapter(writer, ACC_PUBLIC, "time", "J");
        reader.accept(adapter, 0);


        //        //新增加一个方法
        MethodVisitor mw = writer.visitMethod(ACC_PUBLIC + ACC_STATIC, "add", "([Ljava/lang/String;)V", null, null);
        mw.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mw.visitLdcInsn("this is add method print!");
        mw.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        mw.visitInsn(RETURN);
        // this code uses a maximum of two stack elements and two local
        // variables
        mw.visitMaxs(0, 0);
        mw.visitEnd();

        reader.accept(writer, 0);

        byte[] b = writer.toByteArray();
        Utils.writeByte2File("./AddFieldClass.class", b);

    }
}
