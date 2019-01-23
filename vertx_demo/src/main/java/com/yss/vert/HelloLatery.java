package com.yss.vert;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


public class HelloLatery extends AbstractVerticle {

    Logger logger = LoggerFactory.getLogger(HelloLatery.class);

    // Convenience method so you can run it in your IDE
    public static void main(String[] args) {
        Vertx.vertx().deployVerticle("com.yss.vert.HelloLatery");
    }

    @Override
    public void start() {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/hello/:latency").handler(this::handleGetLatery);
        vertx.createHttpServer().requestHandler(router).listen(8080);
    }

    private void handleGetLatery(RoutingContext routingContext) {
        Integer latery = Integer.valueOf(routingContext.request().getParam("latency"));
        vertx.setTimer(latery, id -> {
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "application/json").end("hello world");
        });
    }
}
