package com.future.study.spring.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring官方参考文档
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#websocket
 */
@SpringBootApplication
@ComponentScan
public class ApplicationSpringWebsocket {
    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(ApplicationSpringWebsocket.class,args);
    }
}
