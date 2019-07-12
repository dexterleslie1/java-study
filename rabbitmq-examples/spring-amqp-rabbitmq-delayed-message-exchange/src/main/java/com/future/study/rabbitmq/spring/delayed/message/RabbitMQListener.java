package com.future.study.rabbitmq.spring.delayed.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
public class RabbitMQListener {
    private final static ObjectMapper objectMapper=new ObjectMapper();
    private final static TypeReference<Map<String, String>> typeReference = new TypeReference<Map<String,String>>() {};

    public MessageHandler messageHandler = null;

    @RabbitHandler
    public void process(String message) {
        if(messageHandler != null){
            messageHandler.handle(message);
        }
    }
}
