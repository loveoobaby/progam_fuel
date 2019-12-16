package com.yss.asm.demo.method;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodChangeClassAdapter extends ClassVisitor {

    public MethodChangeClassAdapter(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        //当方法名为execute时，修改方法名为execute1
        if (cv != null && "execute".equals(name)){
            return cv.visitMethod(access, name + "1", descriptor, signature, exceptions);
        }

        //此处的changeMethodContent即为需要修改的方法  ，修改方法內容
        if("changeMethodContent".equals(name)){
            //先得到原始的方法
            MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
            MethodVisitor newMethod = null;
            //访问需要修改的方法
            newMethod = new AsmMethodVisit(mv);
            return newMethod;
        }

        if (cv != null) {
            return cv.visitMethod(access, name, descriptor, signature, exceptions);
        }

        return null;


    }
}
