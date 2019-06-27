package com.xy.demo.redisson.lock;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RedissonSentinelTests {
    private final static Logger logger = Logger.getLogger(RedissonSentinelTests.class);

    private RedissonClient redisson = null;
    private Random random = new Random();

    @Test
    public void test1() throws InterruptedException {
        Date timeStart = new Date();
        int looperOutter = 200;
        final int looperInner = 2000;
        ExecutorService executorService= Executors.newCachedThreadPool();
        for(int i=0;i<looperOutter;i++) {
            final int j = i;
            executorService.submit(new Runnable() {
                public void run() {
                    try{
                        for(int i=0;i<looperInner;i++) {
                            String string1 = String.valueOf(j*looperInner+i);
                            RMap<String, String> rMap = redisson.getMap("redissonmap");
                            rMap.put(string1, string1);
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
                public void run() {
                    try{
                        for(int i=0;i<10000;i++) {
                            int intTemp = random.nextInt(max);
                            RMap<String, String> rMap = redisson.getMap("redissonmap");
                            rMap.get(String.valueOf(intTemp));
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
        Date timeEnd = new Date();
        long milliseconds = timeEnd.getTime() - timeStart.getTime();
        logger.info("耗时" + milliseconds + "毫秒");
    }

    @Before
    public void setup(){
        Config config = new Config();
        config.useSentinelServers().setMasterName("mymaster").addSentinelAddress(
                "redis://192.168.1.111:26381",
                "redis://192.168.1.111:26380",
                "redis://192.168.1.111:26379"
        );
        redisson = Redisson.create(config);
    }

    @After
    public void teardown(){
        if(redisson != null){
            redisson.shutdown();
        }
    }
}
