package com.yss.vert.http;

import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

public class FileUploadHander {


    public static void hander(RoutingContext routingContext) {
        HttpServerRequest req = routingContext.request();
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
    }


}
