package com.yss.asm.demo.addFiledChangeMethod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class ChangeMethodAdaptor extends ClassVisitor {

    private String enhenMethodName;

    public ChangeMethodAdaptor(ClassVisitor classVisitor, String encherMethodName) {
        super(ASM5, classVisitor);
        this.enhenMethodName = encherMethodName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        //此处的changeMethodContent即为需要修改的方法  ，修改方法內容
        if (enhenMethodName.equals(name)) {
            //先得到原始的方法
            MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
            MethodVisitor newMethod = null;
            newMethod = new AsmMethodVisit(mv);
            return newMethod;
        }

        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }

}
