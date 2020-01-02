package com.yss.asm.demo.changeMethodName;

import com.yss.asm.demo.Utils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassReader reader = new ClassReader("com.yss.asm.demo.changeMethodName.TargetChangeMethodNameClass");
        ClassWriter writer = new ClassWriter(0);
        ChangeMethodNameAdapter adapter = new ChangeMethodNameAdapter(writer, "executor", "executor1");
        reader.accept(adapter, 0);

        byte[] b = writer.toByteArray();
        Utils.writeByte2File("./ChangeMethodName.class", b);

    }
}
