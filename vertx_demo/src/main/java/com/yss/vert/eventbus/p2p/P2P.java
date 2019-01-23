package com.yss.vert.eventbus.p2p;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class P2P {

    public static void main(String[] args) {



        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(Receiver.class, new DeploymentOptions());
        vertx.deployVerticle(Sender.class, new DeploymentOptions());



    }
}
