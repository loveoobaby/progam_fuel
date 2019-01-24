package com.yss.vert.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4J {

    private static final Logger logger = LoggerFactory.getLogger(SLF4J.class);

    public static void main(String[] args) {
        // 使用logback.xml
        System.setProperty("vertx.logger-delegate-factory-class-name",
                "io.vertx.core.logging.SLF4JLogDelegateFactory");
        logger.info("I am sl4j {}", "haha");
    }
}
