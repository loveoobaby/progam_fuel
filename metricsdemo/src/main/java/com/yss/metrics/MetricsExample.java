package com.yss.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MetricsExample {
    private static final MetricRegistry registry = new MetricRegistry();
    private static Meter requestMeter = registry.meter("tqs");

    public static void main(String[] args) {
        ConsoleReporter consoleReporter = ConsoleReporter.forRegistry(registry)
                .convertRatesTo(TimeUnit.MINUTES)
                .build();
        consoleReporter.start(60, TimeUnit.SECONDS);

        for (;;){
            handleRequest();
        }
    }

    private static void handleRequest(){
        requestMeter.mark();
        randomSleep();
    }

    private static void randomSleep(){
        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
