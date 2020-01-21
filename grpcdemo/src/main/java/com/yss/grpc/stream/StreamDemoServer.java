package com.yss.grpc.stream;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class StreamDemoServer {

    private static final Logger logger =
            Logger.getLogger(StreamDemoServer.class.getName());

    private final int port;
    private final Server server;

    public StreamDemoServer(int port) {
        this.port = port;
        this.server = ServerBuilder.forPort(port).
                addService(new StreamGreeterImp()).build();
    }


    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }


    public void stop() {
        System.out.println("server stop");
    }

    /**
     * Start serving requests.
     */
    public void start() throws IOException {
        server.start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            StreamDemoServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        StreamDemoServer demoServer = new StreamDemoServer(50070);
        demoServer.start();
        demoServer.blockUntilShutdown();
    }

}
