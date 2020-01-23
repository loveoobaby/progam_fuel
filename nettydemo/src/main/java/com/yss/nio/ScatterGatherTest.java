package com.yss.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * Scattering: 读取文件时，传递一个Buffer数组，依次将数据读到第一个、第二个buffer中、。。。。，并且只有在第一个读满才会读到下一个
 */
public class ScatterGatherTest {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address= new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);

        int messageLegth = 2 + 3 +4;

        ByteBuffer[] byteBuffers = new ByteBuffer[3];
        byteBuffers[0] = ByteBuffer.allocate(2);
        byteBuffers[1] = ByteBuffer.allocate(3);
        byteBuffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true){
            int byteRead = 0;
            while (byteRead < messageLegth){
                long r  = socketChannel.read(byteBuffers);
                byteRead += r;

                Arrays.asList(byteBuffers).stream().map(buffer -> "postion: " + buffer.position() + ", limit: " + buffer.limit()).
                        forEach(System.out::println);
            }

            Arrays.asList(byteBuffers).forEach(byteBuffer -> byteBuffer.flip());

            long byteWrite = 0;
            while (byteWrite < messageLegth){
                byteWrite += socketChannel.write(byteBuffers);
            }

            Arrays.asList(byteBuffers).forEach(ByteBuffer::clear);

            System.out.println("byteread: " + byteRead + ", bytesWrite: " + byteWrite + ", messageLegth: " + messageLegth);

        }



    }

}


















