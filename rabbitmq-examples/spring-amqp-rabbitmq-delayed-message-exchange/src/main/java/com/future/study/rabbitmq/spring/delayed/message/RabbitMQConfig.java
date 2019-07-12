package com.future.study.rabbitmq.spring.delayed.message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME="ak_message_queue"; // 队列名称
    public static final String EXCHANGE_NAME="ak_message_exchange"; // 交换器名称

    @Bean
    public Queue queue(){
        return new Queue(QUEUE_NAME);
    }

    // 配置默认的交互机
    @Bean
    public CustomExchange customExchange(){
        Map<String,Object> args=new HashMap<>();
        args.put("x-delayed-type", "direct");
        // 参数二为类型：必须是x-delayed-message
        return new CustomExchange(EXCHANGE_NAME,"x-delayed-message",true,false,args);
    }

    // 绑定队列到交换器
    @Bean
    public Binding binding(Queue queue, CustomExchange customExchange){
        return BindingBuilder.bind(queue).to(customExchange).with(QUEUE_NAME).noargs();
    }
}
