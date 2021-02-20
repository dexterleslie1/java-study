package com.future.demo.prometheus.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ApiTests {
    @Autowired
    MeterRegistry registry;

    @Test
    public void testCounterApi() {
        Counter counter = Counter
                .builder("instance")
                .description("indicates instance count of the object")
                .tags("dev", "performance")
                .register(registry);
        counter.increment(2.0);
        assertTrue(counter.count() == 2);
        counter.increment(-1);
        assertTrue(counter.count() == 2);
    }

    @Test
    public void testGaugeApi() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        List<String> list = new ArrayList<>(4);
        Gauge gauge = Gauge
                .builder("cache.size", list, List::size)
                .register(registry);
        assertTrue(gauge.value() == 0.0);
        list.add("1");
        list.add("2");
        assertTrue(gauge.value() == 2.0);

        list.remove("1");
        assertTrue(gauge.value() == 1.0);
        list.remove("2");
        assertTrue(gauge.value() == 0.0);

        AtomicInteger atomicInteger = new AtomicInteger(0);
        gauge = Gauge.builder("integer.counter", atomicInteger, AtomicInteger::get)
                .register(registry);
        atomicInteger.incrementAndGet();
        assertTrue(gauge.value() == 1.0);
        atomicInteger.incrementAndGet();
        assertTrue(gauge.value() == 2.0);
        atomicInteger.decrementAndGet();
        assertTrue(gauge.value() == 1.0);
        atomicInteger.set(0);
        assertTrue(gauge.value() == 0.0);
    }
}
