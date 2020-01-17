package com.yss.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;



public class CounterGaugeExample {

    private static final MetricRegistry registy = new MetricRegistry();
    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(registy).
            convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS).build();

    private static final BlockingDeque<Long> queue = new LinkedBlockingDeque<>(100);

    public static void main(String[] args) {
        Counter counter = registy.counter("queue-counter", Counter::new);

        reporter.start(5, TimeUnit.SECONDS);

        new Thread(()->{
            for (;;){
                randomSleep();
                queue.add(System.nanoTime());
                counter.inc();
            }
        }).start();

        new Thread(()->{
            for (;;){

                try {
                    queue.take();
//                    queue.take();
//                    queue.take();
                    counter.dec();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                randomSleep();
            }
        }).start();
    }

    private static void randomSleep(){
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
