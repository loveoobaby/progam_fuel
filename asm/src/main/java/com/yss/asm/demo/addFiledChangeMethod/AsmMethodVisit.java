package com.yss.asm.demo.addFiledChangeMethod;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.*;

public class AsmMethodVisit extends MethodVisitor {
    public AsmMethodVisit(MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
    }

    @Override
    public void visitCode(){
        //此方法在访问方法的头部时被访问到，仅被访问一次
        //此处可插入新的指令
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
        mv.visitFieldInsn(PUTFIELD, "com/yss/asm/demo/addFiledChangeMethod/TargetAddFieldChangeMethodClass", "time", "J");
        super.visitCode();
    }

    @Override
    public void visitInsn(int opcode){
        //此方法可以获取方法中每一条指令的操作类型，被访问多次
        //如应在方法结尾处添加新指令，则应判断：
        if(opcode == Opcodes.RETURN){
            mv.visitVarInsn(ALOAD, 0);
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
            mv.visitVarInsn(ALOAD, 0);
            mv.visitFieldInsn(GETFIELD, "com/yss/asm/demo/addFiledChangeMethod/TargetAddFieldChangeMethodClass", "time", "J");
            mv.visitInsn(LSUB);
            mv.visitFieldInsn(PUTFIELD, "com/yss/asm/demo/addFiledChangeMethod/TargetAddFieldChangeMethodClass", "time", "J");
        }
        super.visitInsn(opcode);
    }


}
