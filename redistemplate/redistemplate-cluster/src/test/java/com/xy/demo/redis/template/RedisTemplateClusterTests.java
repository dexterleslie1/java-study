package com.xy.demo.redis.template;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationRedisTemplateCluster.class})
public class RedisTemplateClusterTests {
    private final static Random random = new Random();

    @Autowired
    private RedisTemplate redisTemplate = null;

    @Test
    public void test() throws InterruptedException {
        int looperOutter = 200;
        int looperInner = 2000;
        ExecutorService executorService= Executors.newCachedThreadPool();
        for(int i=0;i<looperOutter;i++) {
            final int j = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try{
                        for(int i=0;i<looperInner;i++) {
                            String string1 = String.valueOf(j*looperInner+i);
                            redisTemplate.opsForValue().set(string1, string1);
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }finally{
                    }
                }
            });
        }
        executorService.shutdown();
        while(!executorService.awaitTermination(1, TimeUnit.SECONDS));
        System.out.println("redis缓存数据已准备好，准备进行读效率测试");

        final int max = looperOutter*looperInner;
        executorService= Executors.newCachedThreadPool();
        for(int i=0;i<250;i++) {
            final int j = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    RedisProperties.Jedis jedis=null;
                    try{
                        for(int i=0;i<10000;i++) {
                            int intTemp = random.nextInt(max);
                            redisTemplate.opsForValue().get(String.valueOf(intTemp));
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }finally{
                    }
                }
            });
        }
        executorService.shutdown();
        while(!executorService.awaitTermination(1, TimeUnit.SECONDS));
    }
}
