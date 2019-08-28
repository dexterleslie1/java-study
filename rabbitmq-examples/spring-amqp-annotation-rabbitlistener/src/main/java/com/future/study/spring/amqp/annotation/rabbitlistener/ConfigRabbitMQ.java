package com.future.study.spring.amqp.annotation.rabbitlistener;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dexterleslie.Chan
 */
@Configuration
public class ConfigRabbitMQ {

    public static final String exchangeName = "spring-amqp-annotaion-rabbitlistener-exchange-demo";
    static final String queueName = "spring-amqp-annotaion-rabbitlistener-queue-demo";

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        AmqpTemplate amqpTemplate = new RabbitTemplate(connectionFactory);
        return amqpTemplate;
    }
}
