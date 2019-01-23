package com.yss.vert.eventbus.p2p;

import io.vertx.core.DeploymentOptions;

public class P2P {

    public static void main(String[] args) {

        DeploymentOptions options = new DeploymentOptions()
                .setWorker(true).setWorkerPoolName("workers").setWorkerPoolSize(5);
//                .setInstances(5) // matches the worker pool size below
//                .setWorkerPoolName("the-specific-pool")
//                .setWorkerPoolSize(5);

//        Vertx vertx = Vertx.vertx();
//        vertx.deployVerticle(Receiver.class, options);
//        vertx.deployVerticle(Sender.class, new DeploymentOptions());



    }
}
