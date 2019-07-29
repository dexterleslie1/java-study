package com.future.study.rabbitmq.spring.delayed.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @author Dexterleslie.Chan
 */
@Component
public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        logger.info("Received <" + message + ">");
        latch.countDown();
    }

    public void setCountDown(int count) {
        this.latch = new CountDownLatch(count);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
