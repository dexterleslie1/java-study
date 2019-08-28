package com.future.study.spring.amqp.durable;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Dexterleslie.Chan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={ApplicationSpringAMQPDurable.class})
public class ApplicationSpringAMQPDurableTests {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationSpringAMQPDurableTests.class);

    @Autowired
    private AmqpTemplate amqpTemplate = null;
    @Autowired
    private Receiver receiver = null;

    @Test
    public void test1() throws InterruptedException, TimeoutException, UnsupportedEncodingException {
        Message message = MessageBuilder
                .withBody("Durable message".getBytes("utf-8"))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        amqpTemplate.send(ConfigRabbitMQ.ExchangeDurable, null, message);
        logger.debug("Breakpoint holder");
    }
}
