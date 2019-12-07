package com.future.demo.java.spring.cloud.stream.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dexterleslie@gmail.com
 */
@Component
@EnableBinding(SourceCustomize.class)
public class ProducerService {
    @Autowired
    private SourceCustomize sourceCustomize = null;

    /**
     *
     */
    public void produceDestination1() {
        Map<String, String> payload = new HashMap<>();
        payload.put("producer", "producer1");
        Message<Map<String, String>> message =
                MessageBuilder
                        .withPayload(payload)
                        .setHeader(
                                "messageType",
                                "com.future.demo.java.stream.messageType1")
                        .build();
        this.sourceCustomize.outputCustomize().send(message);
    }

    /**
     *
     */
    public void produceDestination2() {
        Map<String, String> payload = new HashMap<>();
        payload.put("producer", "producer2");
        Message<Map<String, String>> message =
                MessageBuilder
                        .withPayload(payload)
                        .setHeader(
                                "messageType",
                                "com.future.demo.java.stream.messageType2")
                        .build();
        this.sourceCustomize.outputCustomize().send(message);
    }

    /**
     *
     */
    public void produceDelay() {
        Map<String, String> payload = new HashMap<>();
        payload.put("producer", "producerDelay");
        Message<Map<String, String>> message =
                MessageBuilder
                        .withPayload(payload)
                        .setHeader(
                                "messageType",
                                "com.future.demo.java.stream.messageType3")
                        .setHeader("x-delay", 20000)
                        .build();
        this.sourceCustomize.outputCustomizeDelay().send(message);
    }
}
