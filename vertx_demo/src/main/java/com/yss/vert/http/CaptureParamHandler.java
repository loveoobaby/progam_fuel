package com.yss.vert.http;

import io.vertx.ext.web.RoutingContext;

public class CaptureParamHandler {

    public static void handerPathParam(RoutingContext routingContext){
        String param1 = routingContext.request().getParam("param1");
        String param2 = routingContext.request().getParam("param2");
        routingContext.response().end(String.format("get param1 = %s, param2 = %s", param1, param2));
    }
}
