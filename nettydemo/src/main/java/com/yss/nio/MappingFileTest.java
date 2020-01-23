package com.yss.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappingFileTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("MappingFileTest", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        mappedByteBuffer.put(0, (byte)'a');
        mappedByteBuffer.put(1, (byte)'b');
        mappedByteBuffer.put(2, (byte)'c');
        mappedByteBuffer.put(3, (byte)'d');
        mappedByteBuffer.put(4, (byte)'e');

        randomAccessFile.close();

    }
}

