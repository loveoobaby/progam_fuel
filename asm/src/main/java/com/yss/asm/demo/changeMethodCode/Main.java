package com.yss.asm.demo.changeMethodCode;

import com.yss.asm.demo.Utils;
import org.objectweb.asm.*;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

public class Main {

    public static void main(String[] args) throws IOException {
        ClassReader cr = new ClassReader(TargetChangeMethodCodeClass.class.getName());
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new MethodChangeClassAdapter(cw);
        cr.accept(cv, Opcodes.ASM5);

        byte[] code = cw.toByteArray();

        Utils.writeByte2File("./AA.class" ,code);
    }

}
