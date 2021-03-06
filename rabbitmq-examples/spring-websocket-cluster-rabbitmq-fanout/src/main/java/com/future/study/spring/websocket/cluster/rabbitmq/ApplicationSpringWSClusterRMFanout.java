package com.future.study.spring.websocket.cluster.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Dexterleslie.Chan
 */
@SpringBootApplication
@ImportResource(locations = {"classpath:spring-quartz.xml"})
public class ApplicationSpringWSClusterRMFanout {
    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(ApplicationSpringWSClusterRMFanout.class, args);
    }
}
