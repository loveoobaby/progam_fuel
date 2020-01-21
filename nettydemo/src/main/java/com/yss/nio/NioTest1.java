package com.yss.nio;

import java.nio.IntBuffer;
import java.util.concurrent.ThreadLocalRandom;

/*
  1. java.io中最为核心的概念是流，面向流的编程。一个流不可能即是输入流又是输出流。
  2. java.nio中拥有3个核心的概念：Selector、Channel、Buffer

                 Selector  ---- thread
                /   |    \
              ch1  ch2   ch3
               |    |     |
              Buf1 Buf2  Buf3

      在java.nio中，我们是面向块（block）或是缓冲区（buffer）编程的。buffer本身就是一块内存，底层实现上是数组。
      数据的读写都是通过Buffer实现的。

      channel指的是可以向其写入数据或读取数据的对象，类似于java.io中的流。

      所有的数据读写都是通过Buffer来进行的，永远不会出现直接向Channel写入数据或直接从Channel中读取数据的情况。

      与Stream不同的是，Channel是双向的，一个流只可能是InputStream或是OutputStream，Channel打开后则可以进行读取、写入或读写。

      由于Channel是双向的，因此它能更好地反应出底层操作系统的真实情况；在Linux系统中，底层操作系统的通道就是双向的。

   3. Buffer中三个重要状态属性：position、limit、capacity
        capacity: buffer的最大容量
        limit：
        position



 */
public class NioTest1 {

    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(ThreadLocalRandom.current().nextInt(1000));
        }
        System.out.println("before flip: limit =  " + buffer.limit());
        buffer.flip();
        System.out.println("after flip: limit =  " + buffer.limit());

        while (buffer.hasRemaining()){
            System.out.println("postion = " + buffer.position() + " , limit = " + buffer.limit());
            System.out.println(buffer.get() + " ");
        }

    }
}
