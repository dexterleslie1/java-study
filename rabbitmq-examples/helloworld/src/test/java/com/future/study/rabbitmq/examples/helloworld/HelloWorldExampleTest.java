package com.future.study.rabbitmq.examples.helloworld;

import com.rabbitmq.client.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
 * This test case is demonstrating for rabbitmq java client connect to rabbitmq server
 * @author Dexterleslie.Chan
 */
public class HelloWorldExampleTest {
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

    @Test
    public void test_tutorial_helloworld() throws IOException, TimeoutException, InterruptedException {
        String queueName = "rabbitmq-examples-tutorial-helloworld";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        Connection connection = connectionFactory.newConnection();
        Connection connectionConsumer = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(queueName, false, false, false, null);

        int totalMessageProduce = 1000;
        List<String> listMessageConsume = new ArrayList<String>();
        CountDownLatch countDownLatch = new CountDownLatch(totalMessageProduce);
        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                listMessageConsume.add(message);
                countDownLatch.countDown();
            }
        };
        // Ready to consume rabbitmq message
        Channel channelConsumer = connectionConsumer.createChannel();
        channelConsumer.basicConsume(queueName, true, deliverCallback, consumerTag -> {});

        List<String> listMessageProduce = new ArrayList<String>();
        for(int i=0 ; i<totalMessageProduce; i++){
            String message = UUID.randomUUID().toString();
            listMessageProduce.add(message);
        }
        for(int i=0; i<totalMessageProduce; i++){
            String message = listMessageProduce.get(i);
            channel.basicPublish("", queueName, null, message.getBytes(StandardCharsets.UTF_8));
        }

        if(!countDownLatch.await(5000, TimeUnit.MILLISECONDS)){
            throw new TimeoutException();
        }

        connection.close();
        connectionConsumer.close();

        Collections.sort(listMessageProduce);
        Collections.sort(listMessageConsume);
        Assert.assertArrayEquals(listMessageProduce.toArray(new String[]{}), listMessageConsume.toArray(new String[]{}));
    }
}
