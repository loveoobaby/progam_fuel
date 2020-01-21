package com.yss.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest2 {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("niotest.txt");
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(512);
        channel.read(buffer);

        buffer.flip();
        while (buffer.hasRemaining()){
            byte b = buffer.get();
            System.out.print((char)b);
        }

        buffer.clear();

        channel.close();
        fileInputStream.close();



    }
}
