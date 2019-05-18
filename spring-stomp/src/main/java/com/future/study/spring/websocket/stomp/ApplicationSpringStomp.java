package com.future.study.spring.websocket.stomp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * Spring官方参考文档
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#websocket-stomp
 * 
 * @author Dexterleslie
 * @date 2018年08月06日
 * @time 12:20
 */
@SpringBootApplication
public class ApplicationSpringStomp {
    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(ApplicationSpringStomp.class,args);
    }
}
