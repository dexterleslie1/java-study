package com.future.study.spring.amqp.acknowledge;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @author Dexterleslie.Chan
 */
@Component
public class Receiver implements ChannelAwareMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(2);


    public CountDownLatch getLatch() {
        return latch;
    }

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        logger.info("Received <" + message + ">");
        latch.countDown();
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        // channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);

        // 抛出异常时，消息不会自动ack并且会被requeue
        // boolean b = true;
        // if(b) {
        //     throw new Exception("testing exception");
        // }
    }
}
