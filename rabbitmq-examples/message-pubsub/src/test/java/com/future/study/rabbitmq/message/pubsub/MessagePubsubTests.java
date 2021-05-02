package com.future.study.rabbitmq.message.pubsub;

import com.rabbitmq.client.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class MessagePubsubTests {
    String host;
    String username;
    String password;

    @Before
    public void setup() {
        String host = System.getenv("host");
        String username = System.getenv("username");
        String password = System.getenv("password");

        this.host = host;
        this.username = username;
        this.password = password;
    }

    /**
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws IOException, TimeoutException, InterruptedException {
        String exchangeName = "pubsub#exchange#" + UUID.randomUUID().toString();

        int totalMessages = 10;
        AtomicInteger atomicInteger1 = new AtomicInteger(0);

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

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
                atomicInteger1.incrementAndGet();
            }
        }, consumerTag -> {});

        channel = connectionQueue2.createChannel();
        String queueName2 = channel.queueDeclare().getQueue();
        channel.queueBind(queueName2, exchangeName, "");
        channel.basicConsume(queueName2, true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                atomicInteger1.incrementAndGet();
            }
        }, consumerTag -> {});

        for(int i=0; i<totalMessages; i++){
            String message = UUID.randomUUID().toString();
            channelExchange.basicPublish(exchangeName, "", null, message.getBytes());
        }

        Thread.sleep(2000);

        connectionExchange.close();
        connectionQueue1.close();
        connectionQueue2.close();

        Assert.assertEquals(20, atomicInteger1.get());
    }
}
