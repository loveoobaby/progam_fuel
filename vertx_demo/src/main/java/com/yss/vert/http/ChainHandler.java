package com.yss.vert.http;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class ChainHandler {

    public static void hander1(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        // enable chunked responses because we will be adding data as
        // we execute over other handlers. This is only required once and
        // only if several handlers do output.
        response.setChunked(true);

        response.write("route1\n");

        // Call the next matching route after a 5 second delay
        routingContext.vertx().setTimer(5000, tid -> routingContext.next());
    }


    public static void hander2(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.write("route2\n");

        // Call the next matching route after a 5 second delay
        routingContext.vertx().setTimer(5000, tid -> routingContext.next());
    }

    public static void hander3(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.write("route3\n");

        // Now end the response
        routingContext.response().end();
    }



}
