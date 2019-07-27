package com.future.study.spring.amqp.configuration;

import com.future.study.spring.amqp.demo.configuration.ApplicationSpringAMQPConfigDemo;
import com.future.study.spring.amqp.demo.configuration.ConfigRabbitMQ;
import com.future.study.spring.amqp.demo.configuration.Receiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author Dexterleslie.Chan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={ApplicationSpringAMQPConfigDemo.class})
public class ApplicationSpringAMQPConfigDemoTests {
    @Autowired
    private AmqpTemplate amqpTemplate = null;
    @Autowired
    private Receiver receiver = null;

    @Test
    public void test1() throws InterruptedException {
        amqpTemplate.convertAndSend(ConfigRabbitMQ.topicExchangeName, "foo.bar.baz", "Hello from RabbitMQ!");
        receiver.getLatch().await(2000, TimeUnit.MILLISECONDS);
    }
}
