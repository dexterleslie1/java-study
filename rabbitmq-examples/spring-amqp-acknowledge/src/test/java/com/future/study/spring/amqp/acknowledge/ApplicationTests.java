package com.future.study.spring.amqp.acknowledge;

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
@SpringBootTest(classes={Application.class})
public class ApplicationTests {
    @Autowired
    private AmqpTemplate amqpTemplate = null;
    @Autowired
    private Receiver receiver = null;

    @Test
    public void test1() throws InterruptedException, TimeoutException {
        for(int i=0; i<2; i++) {
            amqpTemplate.convertAndSend(Config.topicExchangeName, null, "Hello from RabbitMQ!" + i);
        }
        if(!receiver.getLatch().await(2000, TimeUnit.MILLISECONDS)) {
            throw new TimeoutException();
        }
    }
}
