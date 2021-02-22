package com.future.demo.spring.boot.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    public static void main(String []args){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @ConditionalOnProperty(prefix = "spring.boot.test.demo", value="testing", havingValue = "false", matchIfMissing = true)
    TestService testService() {
        TestService service = new TestService();
        return service;
    }
}
