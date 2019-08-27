package com.future.study.spring.amqp.fanout;

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

    public static final String topicExchangeName = "spring-amqp-fanout-exchange-demo";
    static final String queueName = "spring-amqp-fanout-queue-demo";

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
    Queue queue() {
        // 注意：spring-amqp+rabbitmq不能使用命名queue实现广播，
        // 经过测试两个jvm进程使用同名queue绑定到exchange，
        // rabbitmq系统会采用ribbon负载均衡方式一次只能有其中一个同名queue接收到消息，
        // 想要实现消息广播，需要使用AnonymousQueue绑定到exchange，
        // 在此情况每个AnonymousQueue都能够接收到到消息
        //return new Queue(queueName, false, false, true);
        return new AnonymousQueue();
    }

    @Bean
    FanoutExchange exchange() {
        return new FanoutExchange(topicExchangeName, false, true);
    }

    @Bean
    Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addQueues(queue());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
