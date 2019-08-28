package com.future.study.spring.amqp.durable;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dexterleslie.Chan
 */
@Configuration
public class ConfigRabbitMQ {
    static final String rabbitMQHost = "192.168.1.158";
    static final int rabbitMQPort = 5672;
    static final String rabbitMQUsername = "guest";
    static final String rabbitMQPassword = "guest";

    static final String ExchangeDurable = "spring-amqp-durable-exchange-durable";
    static final String ExchangeNondurable = "spring-amqp-durable-exchange-nondurable";
    static final String QueueDurable = "spring-amqp-durable-queue-durable";
    static final String QueueNondurable = "spring-amqp-durable-queue-nondurable";


    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(rabbitMQHost, rabbitMQPort);
        connectionFactory.setUsername(rabbitMQUsername);
        connectionFactory.setPassword(rabbitMQPassword);
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        AmqpTemplate amqpTemplate = new RabbitTemplate(connectionFactory);
        return amqpTemplate;
    }

    @Bean
    Queue queueDurable() {
        return new Queue(QueueDurable, true, false, false);
    }

    @Bean
    Queue queueNondurable() {
        return new Queue(QueueNondurable, false, false, false);
    }

    @Bean
    FanoutExchange exchangeDurable() {
        return new FanoutExchange(ExchangeDurable, true, false);
    }

    @Bean
    FanoutExchange exchangeNondurable() {
        return new FanoutExchange(ExchangeNondurable, false, false);
    }

    @Bean
    Binding bindingDurable(FanoutExchange exchangeDurable, Queue queueDurable) {
        return BindingBuilder.bind(queueDurable).to(exchangeDurable);
    }

    @Bean
    Binding bindingNondurable(FanoutExchange exchangeNondurable, Queue queueNondurable) {
        return BindingBuilder.bind(queueNondurable).to(exchangeNondurable);
    }

    @Bean
    SimpleMessageListenerContainer containerDurable(
            ConnectionFactory connectionFactory,
            Queue queueDurable,
            Receiver receiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addQueues(queueDurable);
        container.setMessageListener(new MessageListenerAdapter(receiver, "receiveMessage"));
        return container;
    }

    @Bean
    SimpleMessageListenerContainer containerNondurable(
            ConnectionFactory connectionFactory,
            Queue queueNondurable,
            Receiver receiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addQueues(queueNondurable);
        container.setMessageListener(new MessageListenerAdapter(receiver, "receiveMessage"));
        return container;
    }
}
