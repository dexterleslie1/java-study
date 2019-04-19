package com.future.study.rabbitmq.examples.helloworld;

import com.rabbitmq.client.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This test case is demonstrating for rabbitmq java client connect to rabbitmq server
 * @author Dexterleslie.Chan
 */
public class HelloWorldExampleTest {
    private String messageConsume = null;
    @Test
    public void test_tutorial_helloworld() throws IOException, TimeoutException, InterruptedException {
        String queueName = "rabbitmq-examples-tutorial-helloworld";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Config.RabbitMQServerHost);
        connectionFactory.setUsername(Config.RabbitMQUsername);
        connectionFactory.setPassword(Config.RabbitMQPassword);

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);

        String messageToPublish = UUID.randomUUID().toString();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                messageConsume = new String(delivery.getBody(), StandardCharsets.UTF_8);
                countDownLatch.countDown();
            }
        };
        // Ready to consume rabbitmq message
        channel.basicConsume(queueName, deliverCallback, consumerTag -> {});

        channel.basicPublish("", queueName, null, messageToPublish.getBytes(StandardCharsets.UTF_8));

        countDownLatch.await(2000, TimeUnit.MILLISECONDS);

        connection.close();
        Assert.assertEquals(messageToPublish, messageConsume);
    }
}
