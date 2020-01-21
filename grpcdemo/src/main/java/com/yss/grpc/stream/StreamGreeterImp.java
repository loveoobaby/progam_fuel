package com.yss.grpc.stream;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class StreamGreeterImp extends GreeterStreamGrpc.GreeterStreamImplBase {

    private static final Logger logger =
            Logger.getLogger(StreamGreeterImp.class.getName());

    @Override
    public StreamObserver<MyRequest> sayHelloRequestStream(StreamObserver<MyReply> responseObserver) {
        return new StreamObserver<MyRequest>() {
            List<MyRequest> requestList = new ArrayList<>();

            @Override
            public void onNext(MyRequest point) {
                requestList.add(point);
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.WARNING, "recordRoute cancelled");
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(MyReply.newBuilder().setMessage("finished").build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void sayHelloResponseStream(MyRequest request, StreamObserver<MyReply> responseObserver) {
        responseObserver.onNext(MyReply.newBuilder().setMessage("张三").build());
        responseObserver.onNext(MyReply.newBuilder().setMessage("李四").build());
        responseObserver.onNext(MyReply.newBuilder().setMessage("王五").build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<MyRequest> sayHelloAllStream(StreamObserver<MyReply> responseObserver) {
        return new StreamObserver<MyRequest>() {
            @Override
            public void onNext(MyRequest point) {
                responseObserver.onNext(MyReply.newBuilder().setMessage(point.getName()).build());
            }

            @Override
            public void onError(Throwable t) {
                logger.log(Level.WARNING, "recordRoute cancelled");
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
