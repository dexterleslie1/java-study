package com.future.study.spring.amqp.fanout;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Dexterleslie.Chan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={ApplicationSpringAMQPFanout.class})
public class ApplicationSpringAMQPFanoutTests {
    @Autowired
    private AmqpTemplate amqpTemplate = null;
    @Autowired
    private Receiver receiver = null;

    @Test
    public void test1() throws InterruptedException, TimeoutException {
        amqpTemplate.convertAndSend(ConfigRabbitMQ.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        if(!receiver.getLatch().await(2000, TimeUnit.MILLISECONDS)) {
            throw new TimeoutException();
        }
    }
}
