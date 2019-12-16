package com.yss.asm.demo.cost;

import com.yss.asm.demo.Utils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestGenerateCostTest {
    public static void main(String[] args) throws IOException {

        String userDir = System.getProperty("user.dir");

        String filePath = userDir + "/target/classes/com/yss/asm/demo/cost/CostTest.class";
        byte[] allClassBytes = Utils.readBytesFile(filePath);

        ClassReader reader = new ClassReader(allClassBytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        reader.accept(new CostClassVisitor(writer), 8);
        byte[] b =  writer.toByteArray();

        Utils.writeByte2File("./CostTestProxy.class", b);
    }
}
