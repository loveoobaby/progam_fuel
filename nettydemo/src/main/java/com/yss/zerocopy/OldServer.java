package com.yss.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OldServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8899);

        while (true){
            Socket socket = serverSocket.accept();
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            try{
                int readCount = 0;
                byte[] byteArray = new byte[4096];
                int totalCount = 0;
                while (readCount != -1){
                    totalCount += readCount;
                    readCount = inputStream.read(byteArray);
                }
                System.out.println("read count : " + totalCount);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
