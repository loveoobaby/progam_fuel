package com.yss.asm.demo.cost;

import com.yss.asm.demo.Utils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class A {
    public static void main(String[] args) throws IOException {

        Path path = Paths.get("/Users/yss/work/progam_fuel/asm/target/classes/com/yss/asm/demo/cost");

        int bytesRead = 0;
        byte[] allClassBytes = null;
        byte[] buffer = new byte[1024];

        try (InputStream input = new BufferedInputStream(new FileInputStream("/Users/yss/work/progam_fuel/asm/target/classes/com/yss/asm/demo/cost/CostTest.class"))){
            while ((bytesRead = input.read(buffer)) != -1) {
                int length = allClassBytes == null ? bytesRead : allClassBytes.length + bytesRead;
                byte[] newAll = new byte[length];
                if(allClassBytes != null){
                    System.arraycopy(allClassBytes, 0, newAll, 0, allClassBytes.length);
                    System.arraycopy(buffer, 0, newAll, allClassBytes.length, bytesRead);
                }else {
                    System.arraycopy(buffer, 0, newAll, 0, bytesRead);
                }
                allClassBytes = newAll;
            }
        }

        ClassReader reader = new ClassReader(allClassBytes);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        reader.accept(new CostClassVisitor(writer), 8);
        byte[] b =  writer.toByteArray();

        Utils.writeByte2File("./CostTestProxy.class", b);
    }
}
