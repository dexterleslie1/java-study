package com.future.demo.redis.pubsub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch countDownLatch;
    private AtomicInteger counter;

    public void startCountDown(int count) {
        countDownLatch = new CountDownLatch(count);
        counter = new AtomicInteger();
    }

    public void await() throws InterruptedException {
        this.wait(5);
    }

    public void await(int seconds) throws InterruptedException {
        this.countDownLatch.await(seconds, TimeUnit.SECONDS);
    }

    public int getCount() {
        return this.counter.get();
    }

    public void receiveMessage(String message) {
        if(countDownLatch!=null) {
            countDownLatch.countDown();
        }
        if(counter!=null) {
            counter.incrementAndGet();
        }
    }
}
