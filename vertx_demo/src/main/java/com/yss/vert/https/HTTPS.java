package com.yss.vert.https;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;


public class HTTPS extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx.vertx().deployVerticle(HTTPS.class, new DeploymentOptions());
  }



  @Override
  public void start() throws Exception {

    HttpServer server =
      vertx.createHttpServer(new HttpServerOptions().setSsl(true).setKeyStoreOptions(
        new JksOptions().setPath("tls/server-keystore.jks").setPassword("wibble")
      ));

    server.requestHandler(req -> {
      req.response().putHeader("content-type", "text/html").end("<html><body><h1>Hello from vert.x!</h1></body></html>");
    }).listen(4443, r->{
      if(r.succeeded()){
        System.out.println("success start https server");
      }else {
        System.out.println(r.cause());
        System.exit(-1);
      }
    });
  }
}
