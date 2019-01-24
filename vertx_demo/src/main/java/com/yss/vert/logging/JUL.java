package com.yss.vert.logging;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;


public class JUL {

    private static Logger logger = LoggerFactory.getLogger("JUL");

    public static void main(String[] args) {
        //默认使用vertx-default-jul-logging.properties
        logger.info("hello, i am jul");
    }

}
