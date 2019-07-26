package com.future.study.rabbitmq.message.topic;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class MessageTopicTests {
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

        CountDownLatch countDownLatchQueue1 = new CountDownLatch(2);
        CountDownLatch countDownLatchQueue2 = new CountDownLatch(5);

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Config.RabbitMQServerHost);
        connectionFactory.setUsername(Config.RabbitMQUsername);
        connectionFactory.setPassword(Config.RabbitMQPassword);

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
                countDownLatchQueue1.countDown();
            }
        }, consumerTag -> {});

        channel = connectionQueue2.createChannel();
        String queueName2 = channel.queueDeclare().getQueue();
        channel.queueBind(queueName2, exchangeName, routingKey1);
        channel.queueBind(queueName2, exchangeName, routingKey2);
        channel.basicConsume(queueName2, true, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                countDownLatchQueue2.countDown();
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

        if(!countDownLatchQueue1.await(5, TimeUnit.SECONDS)
            || !countDownLatchQueue2.await(5, TimeUnit.SECONDS)){
            throw new TimeoutException();
        }

        connectionExchange.close();
        connectionQueue1.close();
        connectionQueue2.close();
    }
}
