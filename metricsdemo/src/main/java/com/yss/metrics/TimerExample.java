package com.yss.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class TimerExample {
    private static final MetricRegistry registy = new MetricRegistry();
    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(registy)
            .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.SECONDS).build();
    private final static Timer timer = registy.timer("search-result", Timer::new);

    public static void main(String[] args) {
        reporter.start(10, TimeUnit.SECONDS);
        while (true){
            doSearch();
        }
    }

    private static void doSearch(){
        Timer.Context context = timer.time();
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            context.stop();
        }
    }

}
