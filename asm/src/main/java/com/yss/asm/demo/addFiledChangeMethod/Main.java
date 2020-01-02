package com.yss.asm.demo.addFiledChangeMethod;


import com.yss.asm.demo.Utils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.*;

/**
 *  新增一个Field，同时在方法中使用这个Field
 */
public class Main {
    public static void main(String[] args) throws IOException {
        ClassReader reader = new ClassReader(TargetAddFieldChangeMethodClass.class.getName());
        ClassWriter writer = new ClassWriter(0);
        AddFieldAdapter addFieldAdapter = new AddFieldAdapter(writer, ACC_PUBLIC, "timer", "J");
        ChangeMethodAdaptor changeMethodNameAdapter = new ChangeMethodAdaptor(addFieldAdapter, "executor");

        reader.accept(changeMethodNameAdapter, 0);
        byte[] b = writer.toByteArray();
        Utils.writeByte2File("./C.class", b);

    }
}
