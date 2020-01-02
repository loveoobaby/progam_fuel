package com.yss.asm.demo.changeMethodName;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ChangeMethodNameAdapter extends ClassVisitor {

    private String oldName;
    private String newName;

    public ChangeMethodNameAdapter(ClassVisitor classVisitor, String oldName, String newName) {
        super(Opcodes.ASM5, classVisitor);
        this.oldName = oldName;
        this.newName = newName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (oldName.equals(name)){
            return super.visitMethod(access, newName, descriptor, signature, exceptions);
        }
        return super.visitMethod(access, name, descriptor, signature, exceptions);
    }
}
