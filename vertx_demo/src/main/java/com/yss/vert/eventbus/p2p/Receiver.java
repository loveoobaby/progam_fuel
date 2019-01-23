package com.yss.vert.eventbus.p2p;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

import java.util.Date;

public class Receiver extends AbstractVerticle {

  @Override
  public void start() throws Exception {

    EventBus eb = vertx.eventBus();

    eb.consumer("ping-address", message -> {

      System.out.println(new Date() + "Received message: " + message.body() + " " + Thread.currentThread());
      // Now send back reply
      message.reply("pong!");
    });

    System.out.println("Receiver ready!");
  }
}
