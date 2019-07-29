//package com.future.study.rabbitmq.spring.delayed.message;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//@RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
//public class RabbitMQListener {
//    public MessageHandler messageHandler = null;
//
//    @RabbitHandler
//    public void process(String message) {
//        if(messageHandler != null){
//            messageHandler.handle(message);
//        }
//    }
//}
