package com.future.study.android.media.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author Dexterleslie.Chan
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
