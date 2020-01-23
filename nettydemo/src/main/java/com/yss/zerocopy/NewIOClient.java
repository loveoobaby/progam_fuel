package com.yss.zerocopy;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8899));
        socketChannel.configureBlocking(true);

        String fileName = "/Users/yss/Downloads/ubuntu-18.04.1-desktop-amd64.iso";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();

        long startTime = System.currentTimeMillis();
        long count = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("耗时：" + (System.currentTimeMillis() - startTime));

    }
}
