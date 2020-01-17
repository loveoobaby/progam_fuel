package com.yss.metrics;

import com.codahale.metrics.CachedGauge;
import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CachedGaugeExample {

    private static final MetricRegistry REGISTRY = new MetricRegistry();
    private static final ConsoleReporter REPORTER = ConsoleReporter.forRegistry(REGISTRY).
            convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.SECONDS).build();

    public static void main(String[] args) throws InterruptedException {
        REPORTER.start(10, TimeUnit.SECONDS);
        REGISTRY.gauge("cached-db-size", ()-> {
            return new CachedGauge<Long>(30, TimeUnit.SECONDS) {
                @Override
                protected Long loadValue() {
                    return queryFromDB();
                }
            };
        });

        Thread.currentThread().join();
    }

    private static Long queryFromDB(){
        System.out.println("query from DB");
        return System.currentTimeMillis();
    }

}
