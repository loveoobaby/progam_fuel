package com.yss.metrics;

import com.codahale.metrics.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class DerivativeGaugeExample {

    private final static LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(10)
            .expireAfterAccess(5, TimeUnit.SECONDS)
            .recordStats()
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return s.toUpperCase();
                }
            });

    private final static MetricRegistry REGISTRY = new MetricRegistry();
    private final static ConsoleReporter REPORTER = ConsoleReporter.forRegistry(REGISTRY)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.SECONDS)
            .build();


    public static void main(String[] args) throws InterruptedException {
        REPORTER.start(10, TimeUnit.SECONDS);
        Gauge<CacheStats> cacheStatsGauge = new Gauge<CacheStats>() {
            @Override
            public CacheStats getValue() {
                return cache.stats();
            }
        };
        REGISTRY.register("missCount", new DerivativeGauge<CacheStats, Long>(cacheStatsGauge) {

            @Override
            protected Long transform(CacheStats cachedGauge) {
                return cachedGauge.missCount();
            }
        });
        for (;;){
            business();
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public static void business() {
        cache.getUnchecked("xiao");
    }
}
