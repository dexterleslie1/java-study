package com.future.demo.java.spring.cloud.stream.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author dexterleslie@gmail.com
 */
@Component
@EnableBinding(SinkCustomize.class)
public class ConsumerService {
    private final static Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    /**
     *
     * @param message
     */
    @StreamListener(
            target = SinkCustomize.InputCustomize,
            condition = "headers['messageType']=='com.future.demo.java.stream.messageType1'")
    public void consume1(Message<Map<String, String>> message) {
        Map<String, String> payload = message.getPayload();
        logger.info("consume1收到消息payload：" + payload);
    }

    /**
     *
     * @param message
     */
    @StreamListener(
            target = SinkCustomize.InputCustomize,
            condition = "headers['messageType']=='com.future.demo.java.stream.messageType2'")
    public void consume2(Message<Map<String, String>> message) {
        Map<String, String> payload = message.getPayload();
        logger.info("consume2收到消息payload：" + payload);
    }
}
