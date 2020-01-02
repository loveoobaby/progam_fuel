package com.yss.asm.demo.addFiledChangeMethod;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;

import static org.objectweb.asm.Opcodes.ASM5;

public class AddFieldAdapter extends ClassVisitor {

    private int fAcc;
    private String fName;
    private String fDesc;
    private boolean isFiledPresent;

    public AddFieldAdapter(ClassVisitor cv, int fAcc, String fName, String fDesc) {
        super(ASM5, cv);
        this.fAcc = fAcc;
        this.fName = fName;
        this.fDesc = fDesc;
    }

    @Override
    public void visitEnd() {
        FieldVisitor fv = cv.visitField(fAcc, fName, fDesc, null, null);
        fv.visitEnd();
        super.visitEnd();
    }
}
