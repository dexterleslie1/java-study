package com.xy.demo.redisson.lock;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RedissonLockPerformanceTests {
    private String redisHost = "192.168.1.229";
    private int redisPort = 6379;
    private String redisPassword = "123456";

    private RedissonClient redisson = null;
    private Random random = new Random();

    @Test
    public void test1() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int j=0; j<200; j++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        String key = UUID.randomUUID().toString();
                        RLock mylock = redisson.getLock(key);
                        boolean isLocked = false;
                        try {
                            isLocked = mylock.tryLock(10, 10000, TimeUnit.MILLISECONDS);

                            int milliseconds = random.nextInt(50);
                            if(milliseconds ==0){
                                milliseconds = 2;
                            }
                            Thread.sleep(milliseconds);
                        } catch (InterruptedException e) {
                            if(isLocked){
                                mylock = redisson.getLock(key);
                                if(mylock != null) {
                                    mylock.unlock();
                                }
                            }
                        }
                    }
                }
            });
        }
        executorService.shutdown();
        while(!executorService.awaitTermination(100, TimeUnit.MILLISECONDS));
    }

    @Before
    public void setup(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+redisHost+":"+redisPort).setPassword(redisPassword);
        redisson = Redisson.create(config);
    }

    @After
    public void teardown(){
        if(redisson != null){
            redisson.shutdown();
        }
    }
}
