package com.future.study.spring.websocket.cluster.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dexterleslie.Chan
 */
@Configuration
public class ConfigRabbitMQ {
    private final static Logger logger = LoggerFactory.getLogger(ConfigRabbitMQ.class);

    public final static String ExchangeName = "chat.message.demo.exchange";

    @Value(value="${spring.rabbitmq.host}")
    private String rabbitMQHost;
    @Value(value="${spring.rabbitmq.port}")
    private int rabbitMQPort;
    @Value(value="${spring.rabbitmq.username}")
    private String rabbitMQUsername;
    @Value(value="${spring.rabbitmq.password}")
    private String rabbitMQPassword;

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMQHost, rabbitMQPort);
        connectionFactory.setUsername(rabbitMQUsername);
        connectionFactory.setPassword(rabbitMQPassword);
        return connectionFactory;
    }

    @Bean
    public Queue queue() {
        Queue queue = new AnonymousQueue();
        return queue;
    }

    @Bean
    public FanoutExchange exchange(){
        FanoutExchange exchange = new FanoutExchange(ExchangeName, false, false);
        return exchange;
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        AmqpTemplate amqpTemplate = new RabbitTemplate(connectionFactory);
        return amqpTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory,
                                                                   Queue queue){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setConcurrentConsumers(100);
        container.addQueues(queue);
        MessageListenerAdapter adapter = new MessageListenerAdapter(mqHandler());
        adapter.setDefaultListenerMethod("handle");
        container.setMessageListener(adapter);

        return container;
    }

    @Bean
    public MQHandler mqHandler(){
        return new MQHandler();
    }
}
