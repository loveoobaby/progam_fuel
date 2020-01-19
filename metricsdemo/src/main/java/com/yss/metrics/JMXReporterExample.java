package com.yss.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class JMXReporterExample {

    private final static MetricRegistry mRegistry = new MetricRegistry();
    private static final Counter counter = new Counter();
    private static final Timer timer = new Timer();

    static {
        mRegistry.register("counter", counter);
        mRegistry.register("timer", timer);
    }
    public static void main(String[] args) {
        JmxReporter jmxReporter = JmxReporter.forRegistry(mRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.SECONDS).build();
        jmxReporter.start();

        while (true){
            doUpload();
        }
    }

    public static void doUpload(){
        counter.inc();
        Timer.Context context = timer.time();
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            context.stop();
        }
    }

}
