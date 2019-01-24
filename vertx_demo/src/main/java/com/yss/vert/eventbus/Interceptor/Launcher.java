package com.yss.vert.eventbus.Interceptor;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;

public class Launcher {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.eventBus().addInboundInterceptor( deliveryContext -> {
            // 获取message
            Message msg = deliveryContext.message();
            /*
               进行自定义拦截处理
             */
            // 如果不写，后续消费者将无法得到消息
            deliveryContext.next();
        });

        vertx.deployVerticle(Receiver.class, new DeploymentOptions());
        vertx.deployVerticle(Sender.class, new DeploymentOptions());


    }
}
