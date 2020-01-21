package com.yss.grpc.stream;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;

public class StreamDemoClient {

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 50070).usePlaintext().build();

        {
            // 客户端发出一个请求，返回一个stream
            GreeterStreamGrpc.GreeterStreamBlockingStub blockingStub = GreeterStreamGrpc.newBlockingStub(managedChannel);
            Iterator<MyReply> replyIterator = blockingStub.sayHelloResponseStream(MyRequest.newBuilder().setName("lisi").build());
            while (replyIterator.hasNext()){
                System.out.println(replyIterator.next().getMessage());
            }
        }


        {
            System.out.println("--------------------------------");
            System.out.println("客户端使用stream，服务端返回一个响应");
            // 客户端使用stream，服务端返回一个响应
            GreeterStreamGrpc.GreeterStreamStub asyncStub = GreeterStreamGrpc.newStub(managedChannel);

            StreamObserver<MyReply> responseObserver  = new StreamObserver<MyReply>() {
                @Override
                public void onNext(MyReply value) {
                    System.out.println(value);
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println(t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("complete");
                }
            };
            StreamObserver<MyRequest> myRequestStreamObserver = asyncStub.sayHelloRequestStream(responseObserver);
            myRequestStreamObserver.onNext(MyRequest.newBuilder().setName("zhangsan").build());
            myRequestStreamObserver.onNext(MyRequest.newBuilder().setName("lisi").build());
            myRequestStreamObserver.onCompleted();

        }

        {
            // 客户端与服务器端都采用stream方式
            System.out.println("--------------------------------");
            System.out.println("客户端与服务器端都采用stream方式");
            GreeterStreamGrpc.GreeterStreamStub asyncStub = GreeterStreamGrpc.newStub(managedChannel);

            StreamObserver<MyReply> responseObserver  = new StreamObserver<MyReply>() {
                @Override
                public void onNext(MyReply value) {
                    System.out.println(value);
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println(t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("complete");
                }
            };

            StreamObserver<MyRequest> requestStreamObserver = asyncStub.sayHelloAllStream(responseObserver);
            requestStreamObserver.onNext(MyRequest.newBuilder().setName("zhangsan").build());
            requestStreamObserver.onCompleted();

        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
