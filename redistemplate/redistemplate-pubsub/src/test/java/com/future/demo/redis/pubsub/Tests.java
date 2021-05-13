package com.future.demo.redis.pubsub;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class Tests {
    private final static Logger LOGGER = LoggerFactory.getLogger(Tests.class);

    @Autowired
    private RedisTemplate redisTemplate = null;
    @Autowired
    Receiver receiver;

    @Test
    public void test() throws InterruptedException {
        int count = new Random().nextInt(100);
        if(count<=0) {
            count = 5;
        }

        receiver.startCountDown(count);
        for(int i=0; i<count; i++) {
            this.redisTemplate.convertAndSend(Config.Channel, String.valueOf(i));
        }
        receiver.await();
        Assert.assertEquals(count, receiver.getCount());
    }

    @Test
    public void testPerformance() throws InterruptedException {
        int concurrentThreads = 20;
        int eachThreadPublishMessageCount = 10000;

        long startTime = new Date().getTime();
        receiver.startCountDown(concurrentThreads*eachThreadPublishMessageCount);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0; i<concurrentThreads; i++) {
            final int j = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    for(int i=0; i<eachThreadPublishMessageCount; i++) {
                        int number = j*eachThreadPublishMessageCount + i;
                        redisTemplate.convertAndSend(Config.Channel, String.valueOf(number));
                    }
                }
            });
        }

        receiver.await(60);
        Assert.assertEquals(concurrentThreads*eachThreadPublishMessageCount, receiver.getCount());
        long endTime = new Date().getTime();
        LOGGER.info("耗时：" + (endTime-startTime) + "毫秒");
    }
}
