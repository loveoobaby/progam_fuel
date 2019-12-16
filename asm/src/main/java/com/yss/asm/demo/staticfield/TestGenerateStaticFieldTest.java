package com.yss.asm.demo.staticfield;

import com.yss.asm.demo.Utils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.IOException;

public class TestGenerateStaticFieldTest {
    public static void main(String[] args) throws IOException {

        String userDir = System.getProperty("user.dir");

        String filePath = userDir + "/target/classes/com/yss/asm/demo/staticfield/FieldTest.class";
        byte[] allClassBytes = Utils.readBytesFile(filePath);

        ClassReader reader = new ClassReader(allClassBytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        reader.accept(new AddTimerAdapter(writer), 8);
        byte[] b =  writer.toByteArray();

        Utils.writeByte2File("./FieldTestProxy.class", b);
    }
}
