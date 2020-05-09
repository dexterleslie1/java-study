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
@EnableScheduling
public class Task1 {
	/**
	 * 
	 */
	@Scheduled(cron = "0/2 * * * * ?")
    public void testimer() {
        Date date = new Date();
        System.out.println(date);
    }
}
