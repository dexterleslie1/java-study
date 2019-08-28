package com.future.study.spring.amqp.durable;

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
        boolean b = true;
        if(b) {
            Thread.sleep(2000);
            throw new Exception("Exception is used for prevent message from confirming");
        }
    }
}
