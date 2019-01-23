package com.yss.vert.http;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class BlockingHander {

    public static void hander(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        response.end("block end!");
    }


}
