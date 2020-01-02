package com.yss.asm.demo.removeMethod;



import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class RemoveMethodAdapter extends ClassVisitor {
    private String methodName;
    public RemoveMethodAdapter(ClassVisitor cv, String name) {
        super(ASM5, cv);
        methodName = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if(name.equals(this.methodName)){
            return null;
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
