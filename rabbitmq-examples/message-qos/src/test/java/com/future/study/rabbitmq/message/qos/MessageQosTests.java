package com.future.study.rabbitmq.message.qos;

import com.rabbitmq.client.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.Timeout;

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
public class MessageQosTests {
    /**
     * 程序在没有消费者订阅队列前一次发送100个消息到消息队列，
     * 发送完毕后注册两个消费者qos分别为30%和70%比例分配100个消息到两个消费中
     * @throws IOException
     * @throws TimeoutException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws IOException, TimeoutException, InterruptedException {
        String queueName = UUID.randomUUID().toString();

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Config.RabbitMQServerHost);
        connectionFactory.setUsername(Config.RabbitMQUsername);
        connectionFactory.setPassword(Config.RabbitMQPassword);

        Connection connection = connectionFactory.newConnection();
        Connection connectionWorker1 = connectionFactory.newConnection();
        Connection connectionWorker2 = connectionFactory.newConnection();

        int total = 100;
        double percentageWorker1 = 0.3;
        int countDownWorker1 = (int)(total*percentageWorker1);
        int countDownWorker2 = (int)(total*(1-percentageWorker1));
        AtomicInteger atomicIntegerWorker1 = new AtomicInteger();
        AtomicInteger atomicIntegerWorker2 = new AtomicInteger();

        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        for(int i=0; i<total; i++) {
            String message = UUID.randomUUID().toString();
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
        }

        Channel channelWorker1 = connectionWorker1.createChannel();
        channelWorker1.basicQos(countDownWorker1, false);
        channelWorker1.basicConsume(queueName, false, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                atomicIntegerWorker1.incrementAndGet();
                channelWorker1.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        }, consumerTag -> {});
        Channel channelWorker2 = connectionWorker2.createChannel();
        channelWorker2.basicQos(countDownWorker2, false);
        channelWorker2.basicConsume(queueName, false, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                atomicIntegerWorker2.incrementAndGet();
                channelWorker2.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        }, consumerTag -> {});

        int awaitTimeoutInSeconds = 1;
        Thread.sleep(awaitTimeoutInSeconds*1000);

        connection.close();
        connectionWorker1.close();
        connectionWorker2.close();

        Assert.assertEquals(countDownWorker1, atomicIntegerWorker1.get());
        Assert.assertEquals(countDownWorker2, atomicIntegerWorker2.get());
    }
}
