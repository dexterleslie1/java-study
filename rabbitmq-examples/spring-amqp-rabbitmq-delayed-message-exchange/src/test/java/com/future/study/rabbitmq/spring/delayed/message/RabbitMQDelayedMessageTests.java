package com.future.study.rabbitmq.spring.delayed.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={
                SpringAMQPRabbitMQDelayedMessageApplication.class
        },
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class RabbitMQDelayedMessageTests {
    @Autowired
    private RabbitMQListener rabbitMQListener = null;
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Test
    public void test1() throws TimeoutException, InterruptedException {
        int totalCount = 10000;
        CountDownLatch countDownLatch = new CountDownLatch(totalCount);
        MessageHandler handler = new MessageHandler() {
            @Override
            public void handle(String message) {
                countDownLatch.countDown();
            }
        };
        rabbitMQListener.messageHandler = handler;

        for(int i=0; i<totalCount; i++){
            this.rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, "444", MessagePostProcessortVariable);
        }

        if(!countDownLatch.await(60, TimeUnit.SECONDS)){
            throw new TimeoutException();
        }
    }

    private final static MessagePostProcessor MessagePostProcessortVariable = new MessagePostProcessor() {
        @Override
        public Message postProcessMessage(Message message) throws AmqpException {
            message.getMessageProperties().setHeader("x-delay", 2000);
            return message;
        }
    };
}
