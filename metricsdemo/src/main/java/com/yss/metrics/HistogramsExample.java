package com.yss.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class HistogramsExample {
    private static final MetricRegistry registy = new MetricRegistry();
    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(registy)
            .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.SECONDS).build();
    private final static Histogram histogram = registy.histogram("search-result");

    public static void main(String[] args) {
        reporter.start(10, TimeUnit.SECONDS);
        while (true){
            doSearch();
        }
    }

    private static void doSearch(){
        histogram.update(ThreadLocalRandom.current().nextInt(10));
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
