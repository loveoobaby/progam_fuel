package com.yss.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.JmxAttributeGauge;
import com.codahale.metrics.MetricRegistry;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.util.concurrent.TimeUnit;

public class JMXAttibuteGaugeExample {

    private static final MetricRegistry registy = new MetricRegistry();
    private static final ConsoleReporter reporter = ConsoleReporter.forRegistry(registy)
            .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.SECONDS).build();

    public static void main(String[] args) throws MalformedObjectNameException, InterruptedException {

        registy.register(MetricRegistry.name(JMXAttibuteGaugeExample.class, "HeapMemory"), new JmxAttributeGauge(
                new ObjectName("java.lang:type=Memory"),"HeapMemoryUsage"
        ));

        reporter.start(10, TimeUnit.SECONDS);

        Thread.currentThread().join();



    }

}
