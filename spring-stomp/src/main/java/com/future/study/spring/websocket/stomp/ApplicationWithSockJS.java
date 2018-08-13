package com.future.study.spring.websocket.stomp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author Dexterleslie
 * @date 2018年08月06日
 * @time 12:20
 */
@SpringBootApplication
@Import(value={WebSocketConfigWithSockJS.class})
public class ApplicationWithSockJS {
    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(ApplicationWithSockJS.class,args);
    }
}
