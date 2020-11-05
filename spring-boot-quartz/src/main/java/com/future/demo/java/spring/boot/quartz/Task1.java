package com.future.demo.java.spring.boot.quartz;

import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dexterleslie@gmail.com
 *
 */
@Component
public class Task1 {
	/**
	 * 
	 */
	@Scheduled(initialDelay = 0, fixedDelay = 60000)
    public void initialTask() {
        Date date = new Date();
        System.out.println("InitialTask " + date);
    }

    /**
     *
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void cronbTask() {
        Date date = new Date();
        System.out.println("cronbTask " + date);
    }
}
