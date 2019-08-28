package com.future.study.spring.amqp.annotation.rabbitlistener;

import com.future.study.spring.amqp.annotation.rabbitlistener.ApplicationSpringAMQPAnnotationRabbitListener;
import com.future.study.spring.amqp.annotation.rabbitlistener.ConfigRabbitMQ;
import com.future.study.spring.amqp.annotation.rabbitlistener.Receiver;
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
@SpringBootTest(classes={ApplicationSpringAMQPAnnotationRabbitListener.class})
public class ApplicationSpringAMQPAnnotationRabbitListenerTests {
    @Autowired
    private AmqpTemplate amqpTemplate = null;
    @Autowired
    private Receiver receiver = null;

    @Test
    public void test1() throws InterruptedException, TimeoutException {
        for(int i=0; i<2; i++) {
            amqpTemplate.convertAndSend(ConfigRabbitMQ.exchangeName, null, "Hello from RabbitMQ!" + i);
        }
        if(!receiver.getLatch().await(2000, TimeUnit.MILLISECONDS)) {
            throw new TimeoutException();
        }
    }
}
