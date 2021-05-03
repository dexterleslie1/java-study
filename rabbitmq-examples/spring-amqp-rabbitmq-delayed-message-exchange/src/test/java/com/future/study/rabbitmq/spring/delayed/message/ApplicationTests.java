package com.future.study.rabbitmq.spring.delayed.message;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApplicationTests {
    final static long Delay = 3000;

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private Receiver receiver = null;

    @Test
    public void test1() throws TimeoutException, InterruptedException {
        int totalCount = 30;
        receiver.setCountDown(totalCount);

        for(int i=0; i<totalCount; i++){
            this.rabbitTemplate.convertAndSend(Config.EXCHANGE_NAME, "routingKey1", "444", MessagePostProcessortVariable);
        }

        Date startTime = new Date();
        if(!receiver.getLatch().await(60, TimeUnit.SECONDS)){
            throw new TimeoutException();
        }
        Date endTime = new Date();
        long milliseconds = endTime.getTime() - startTime.getTime();
        Assert.assertTrue(milliseconds>=Delay - 500);
    }

    private final static MessagePostProcessor MessagePostProcessortVariable = new MessagePostProcessor() {
        @Override
        public Message postProcessMessage(Message message) throws AmqpException {
            message.getMessageProperties().setHeader("x-delay", Delay);
            return message;
        }
    };
}
