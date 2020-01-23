package com.yss.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

public class CharsetTest {

    public static void main(String[] args) throws IOException {
        RandomAccessFile input = new RandomAccessFile("charSet_in.txt", "rw");
        RandomAccessFile output = new RandomAccessFile("charSet_out.txt", "rw");

        long inputLength = input.length();
        FileChannel inputChannel = input.getChannel();
        FileChannel outputChannel = output.getChannel();

        MappedByteBuffer inputData = inputChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLength);
        Charset charset = Charset.forName("utf-8");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = decoder.decode(inputData);
        ByteBuffer outputData = encoder.encode(charBuffer);

        outputChannel.write(outputData);

        input.close();
        output.close();

    }
}
