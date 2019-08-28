package com.future.study.spring.amqp.annotation.rabbitlistener;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Dexterleslie.Chan
 */
@Component
@RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = ConfigRabbitMQ.queueName, autoDelete = "true"),
        exchange = @Exchange(value=ConfigRabbitMQ.exchangeName, type=ExchangeTypes.FANOUT),
        key = ""
), containerFactory = "rabbitListenerContainerFactory")
public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

    private CountDownLatch latch = new CountDownLatch(2);

    @RabbitHandler
    public void receiveMessage(String message,
                               Channel channel,
                               @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws Exception {
        logger.info("Received <" + message + ">");
        latch.countDown();
        channel.basicAck(deliveryTag, false);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
