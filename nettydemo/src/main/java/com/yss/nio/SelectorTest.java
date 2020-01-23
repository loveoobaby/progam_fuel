package com.yss.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {

    public static void main(String[] args) throws IOException {
        int[] ports = {5000, 5001, 5002, 5003, 5004};

        Selector selector = Selector.open();

        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            serverSocket.bind(address);

            // 注册选择器
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听：" + ports[i]);
        }

        while (true){
            int nums = selector.select();
            Set<SelectionKey> keySet =  selector.selectedKeys();
            System.out.println(keySet.size());

            Iterator<SelectionKey> its = keySet.iterator();
            while (its.hasNext()){
                SelectionKey selectionKey = its.next();
                if(selectionKey.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = channel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }

                if(selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(512);
                    int readBytes = 0;
                    while (true){
                        buffer.clear();
                        int readNums = socketChannel.read(buffer);
                        if(readNums <= 0) break;

                        buffer.flip();
                        socketChannel.write(buffer);

                        readBytes += readNums;
                    }
                    System.out.println("byte read num :" + readBytes);
                }
                its.remove();
            }

        }


    }
}
