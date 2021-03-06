package com.yss.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*
    通过NIO读取文件的步骤：
      1. 从FileInputStream中获取到FileChannel对象
      2. 创建Buffer
      3. 将数据读取到Buffer中
 */
public class NioTest4 {

    public static void main(String[] args) throws IOException {
        FileInputStream in = new FileInputStream("NioTest2.txt");
        FileOutputStream output = new FileOutputStream("out.txt");
        FileChannel inchannel = in.getChannel();
        FileChannel outChannel = output.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1);
        while (true){
            buffer.clear();
            int read = inchannel.read(buffer);
            if(read == -1){
                break;
            }
            buffer.flip();
            outChannel.write(buffer);
        }

        inchannel.close();
        outChannel.close();
        in.close();
        output.close();

    }
}
