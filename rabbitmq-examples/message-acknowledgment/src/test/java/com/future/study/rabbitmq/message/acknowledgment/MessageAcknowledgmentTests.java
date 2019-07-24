package com.future.study.rabbitmq.message.acknowledgment;

import com.rabbitmq.client.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 */
public class MessageAcknowledgmentTests {
    @Test
    public void test1() throws IOException, TimeoutException, InterruptedException {
        String queueName = "rabbitmq-examples-message-acknowledgment";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Config.RabbitMQServerHost);
        connectionFactory.setUsername(Config.RabbitMQUsername);
        connectionFactory.setPassword(Config.RabbitMQPassword);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);
        List<String> listMessageConsume = new ArrayList<String>();
        CountDownLatch countDownLatch = new CountDownLatch(2);
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                listMessageConsume.add(message);
                countDownLatch.countDown();
            }
        };
        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {});

        String message = UUID.randomUUID().toString();
        channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));

        Thread.sleep(1000);
        // 不确认消息关闭连接，下次打开新连接将会继续接收到同一条消息
        connection.close();

        // 此次打开连接将会接收到上一条未确认消息
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        final Channel channelTemporary = channel;
        deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                listMessageConsume.add(message);
                countDownLatch.countDown();
                channelTemporary.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {});
        Thread.sleep(1000);
        connection.close();

        // 此次打开连接不会再接收到任何消息
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                listMessageConsume.add(message);
                countDownLatch.countDown();
            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
        Thread.sleep(1000);
        connection.close();

        if(!countDownLatch.await(5000, TimeUnit.MILLISECONDS)){
            throw new TimeoutException();
        }

        List<String> listMessageProduce = new ArrayList<>();
        listMessageProduce.add(message);
        listMessageProduce.add(message);

        Collections.sort(listMessageProduce);
        Collections.sort(listMessageConsume);
        Assert.assertArrayEquals(listMessageProduce.toArray(new String[]{}), listMessageConsume.toArray(new String[]{}));
    }
}
