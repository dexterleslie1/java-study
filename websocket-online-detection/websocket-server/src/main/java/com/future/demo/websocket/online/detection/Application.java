package com.future.demo.websocket.online.detection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring官方参考文档
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#websocket
 *
 * @author dexterleslie@gmail.com
 */
@SpringBootApplication
@ComponentScan
public class Application {
    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(Application.class,args);
    }
}
