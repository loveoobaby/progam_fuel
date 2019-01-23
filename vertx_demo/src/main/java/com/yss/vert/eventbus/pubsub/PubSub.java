package com.yss.vert.eventbus.pubsub;

import io.vertx.core.Vertx;

public class PubSub {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("com.yss.vert.eventbus.pubsub.Sender");
        vertx.deployVerticle("com.yss.vert.eventbus.pubsub.Receiver");
    }
}
