package com.future.study.rabbitmq.message.pubsub;

import com.rabbitmq.client.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class MessagePubsubTests {
    /**
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws IOException, TimeoutException, InterruptedException {
        String exchangeName = "pubsub#exchange#" + UUID.randomUUID().toString();

        int totalMessages = 10;
        CountDownLatch countDownLatch = new CountDownLatch(totalMessages*2);

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Config.RabbitMQServerHost);
        connectionFactory.setUsername(Config.RabbitMQUsername);
        connectionFactory.setPassword(Config.RabbitMQPassword);

        Connection connectionExchange = connectionFactory.newConnection();
        Connection connectionQueue1 = connectionFactory.newConnection();
        Connection connectionQueue2 = connectionFactory.newConnection();

        Channel channelExchange = connectionExchange.createChannel();
        channelExchange.exchangeDeclare(exchangeName, "fanout");

        Channel channel = connectionQueue1.createChannel();
        String queueName1 = channel.queueDeclare().getQueue();
        channel.queueBind(queueName1, exchangeName, "");
        channel.basicConsume(queueName1, true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                countDownLatch.countDown();
            }
        }, consumerTag -> {});

        channel = connectionQueue2.createChannel();
        String queueName2 = channel.queueDeclare().getQueue();
        channel.queueBind(queueName2, exchangeName, "");
        channel.basicConsume(queueName2, true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                countDownLatch.countDown();
            }
        }, consumerTag -> {});

        for(int i=0; i<totalMessages; i++){
            String message = UUID.randomUUID().toString();
            channelExchange.basicPublish(exchangeName, "", null, message.getBytes());
        }

        if(!countDownLatch.await(5, TimeUnit.SECONDS)){
            throw new TimeoutException();
        }

        connectionExchange.close();
        connectionQueue1.close();
        connectionQueue2.close();
    }
}
