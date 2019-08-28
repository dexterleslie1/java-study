package com.future.study.spring.amqp.annotation.rabbitlistener;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Dexterleslie.Chan
 */
@SpringBootApplication
public class ApplicationSpringAMQPAnnotationRabbitListener {
    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(ApplicationSpringAMQPAnnotationRabbitListener.class, args);
    }
}
