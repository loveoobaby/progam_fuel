package com.yss.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest3 {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("NioTest2.txt");
        FileChannel channel = fileOutputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);
        byte[] message = "hello".getBytes();
        for (int i = 0; i < message.length; i++) {
            buffer.put(message[i]);
        }

        buffer.flip();
        channel.write(buffer);
        channel.close();
        fileOutputStream.close();
    }
}
