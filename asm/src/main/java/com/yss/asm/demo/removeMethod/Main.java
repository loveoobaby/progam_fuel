package com.yss.asm.demo.removeMethod;


import com.yss.asm.demo.Utils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassReader reader = new ClassReader("com.yss.asm.demo.removemethod.TargetRemoveMethodClass");
        ClassWriter writer = new ClassWriter(reader, 0);
        RemoveMethodAdapter adapter = new RemoveMethodAdapter(writer, "method1");
        reader.accept(adapter, 0);

        byte[] b = writer.toByteArray();
        Utils.writeByte2File("./RemoveMethodClass.class", b);
    }
}
