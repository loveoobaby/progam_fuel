package com.yss.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.RatioGauge;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class RatioGaugeExample {

    private static final MetricRegistry REGISTRY = new MetricRegistry();
    private static final ConsoleReporter REPORTER = ConsoleReporter.forRegistry(REGISTRY)
                           .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.SECONDS).build();

    private static final Meter totalMeter = new Meter();
    private static final Meter successMeter = new Meter();


    public static void main(String[] args) {
        REPORTER.start(10, TimeUnit.SECONDS);
        REGISTRY.gauge("success-ratio", ()-> new RatioGauge(){
            @Override
            protected Ratio getRatio() {
                return Ratio.of(successMeter.getCount(), totalMeter.getCount());
            }
        });

        for (;;){
            business();
        }

    }

    private static void business(){
        totalMeter.mark();
        try {
            int x = 1 / ThreadLocalRandom.current().nextInt(10);
            successMeter.mark();
        }catch (Exception e){

        }
    }

    public static void shortSleep(){
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
