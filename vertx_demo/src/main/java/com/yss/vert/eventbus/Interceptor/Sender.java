package com.yss.vert.eventbus.Interceptor;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

/*
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class Sender extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    EventBus eb = vertx.eventBus();


    vertx.setPeriodic(100, v -> {

      eb.send("ping-address", "ping!", reply -> {
        if (reply.succeeded()) {
//          System.out.println(new Date() + " Received reply " + reply.result().body() + " " + Thread.currentThread());
        } else {
//          System.out.println("No reply");
        }
      });


    });

  }
}
