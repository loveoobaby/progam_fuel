package com.yss.zerocopy;

import java.io.*;
import java.net.Socket;

public class OldIOClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8899);
        String fileName = "/Users/yss/Downloads/ubuntu-18.04.1-desktop-amd64.iso";
        InputStream inputStream = new BufferedInputStream(new FileInputStream(fileName));

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];
        int readCount = 0;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = inputStream.read(buffer)) != -1){
             total += readCount;
             dataOutputStream.write(buffer, 0, readCount);
        }

        System.out.println("发送总字节数： " + total + ", 耗时： " + (System.currentTimeMillis() - startTime) );
        dataOutputStream.close();
        socket.close();
        inputStream.close();

    }
}
