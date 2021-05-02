package com.future.study.rabbitmq.message.topic;

import com.rabbitmq.client.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class MessageTopicTests {
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
        String exchangeName = "topic#exchange#" + UUID.randomUUID().toString();
        String routingKey1Prefix = "routingKey1.";
        String routingKey2Prefix = "routingKey2.";
        String routingKey1 = routingKey1Prefix + "*";
        String routingKey2 = routingKey2Prefix + "*";

        AtomicInteger atomicInteger1 = new AtomicInteger(0);
        AtomicInteger atomicInteger2 = new AtomicInteger(0);

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        Connection connectionExchange = connectionFactory.newConnection();
        Connection connectionQueue1 = connectionFactory.newConnection();
        Connection connectionQueue2 = connectionFactory.newConnection();

        Channel channelExchange = connectionExchange.createChannel();
        channelExchange.exchangeDeclare(exchangeName, "topic");

        Channel channel = connectionQueue1.createChannel();
        String queueName1 = channel.queueDeclare().getQueue();
        channel.queueBind(queueName1, exchangeName, routingKey1);
        channel.basicConsume(queueName1, true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                atomicInteger1.incrementAndGet();
            }
        }, consumerTag -> {});

        channel = connectionQueue2.createChannel();
        String queueName2 = channel.queueDeclare().getQueue();
        channel.queueBind(queueName2, exchangeName, routingKey1);
        channel.queueBind(queueName2, exchangeName, routingKey2);
        channel.basicConsume(queueName2, true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                atomicInteger2.incrementAndGet();
            }
        }, consumerTag -> {});

        String message = UUID.randomUUID().toString();
        channelExchange.basicPublish(exchangeName, routingKey1Prefix + message, null, message.getBytes());
        message = UUID.randomUUID().toString();
        channelExchange.basicPublish(exchangeName, routingKey1Prefix + message, null, message.getBytes());

        message = UUID.randomUUID().toString();
        channelExchange.basicPublish(exchangeName, routingKey2Prefix + message, null, message.getBytes());
        message = UUID.randomUUID().toString();
        channelExchange.basicPublish(exchangeName, routingKey2Prefix + message, null, message.getBytes());
        message = UUID.randomUUID().toString();
        channelExchange.basicPublish(exchangeName, routingKey2Prefix + message, null, message.getBytes());

        Thread.sleep(1000);

        connectionExchange.close();
        connectionQueue1.close();
        connectionQueue2.close();

        Assert.assertEquals(2, atomicInteger1.get());
        Assert.assertEquals(5, atomicInteger2.get());
    }
}
