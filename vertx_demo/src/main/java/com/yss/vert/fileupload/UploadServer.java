package com.yss.vert.fileupload;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;


public class UploadServer extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(UploadServer.class, new DeploymentOptions());
    }

    @Override
    public void start() throws Exception {

        Router router = Router.router(vertx);

        router.get("/index.html").handler(StaticHandler.create().setWebRoot("fileupload"));
        router.route("/form").handler(context -> {
            HttpServerRequest req = context.request();
            req.setExpectMultipart(true);
            req.uploadHandler(upload -> {
                upload.exceptionHandler(cause -> {
                    req.response().setChunked(true).end("Upload failed");
                });

                upload.endHandler(v -> {
                    req.response().setChunked(true).end("Successfully uploaded to " + upload.filename());
                });

                upload.streamToFileSystem(upload.filename());
            });

        });

        vertx.createHttpServer().requestHandler(router).listen(8080);
    }
}
