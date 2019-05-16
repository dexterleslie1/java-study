package com.xy.demo.curator.framework;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CuratorFrameworkPerformanceTests {
    private final static Logger logger = Logger.getLogger(CuratorFrameworkPerformanceTests.class);

    private final static String url = "192.168.1.230:2181";
    private CuratorFramework curatorFramework = null;
    private Random random = new Random();

    @Test
    public void test1() throws InterruptedException {
        Date timeStart = new Date();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0; i<200; i++){
            executorService.submit(new Runnable() {
                public void run() {
                    for(int j=0; j<1000; j++){
                        InterProcessSemaphoreMutex lock = null;
                        try{
                            String uuid = UUID.randomUUID().toString();
                            lock = new InterProcessSemaphoreMutex(curatorFramework, uuid);
                            lock.acquire(50, TimeUnit.MILLISECONDS);

                            int randomMilliseconds = random.nextInt(50);
                            if(randomMilliseconds == 0){
                                randomMilliseconds = 2;
                            }
                            Thread.sleep(randomMilliseconds);
                        }catch(Exception ex){
                            if(lock != null && lock.isAcquiredInThisProcess()){
                                try {
                                    lock.release();
                                } catch (Exception e) {
                                }
                            }
			                lock = null;
                        }
                    }
                }
            });
        }
        executorService.shutdown();
        while(!executorService.awaitTermination(100, TimeUnit.MILLISECONDS));
        Date timeEnd = new Date();
        long milliseconds = timeEnd.getTime() - timeStart.getTime();
        logger.info("耗时" + milliseconds + "毫秒");
    }

    @Before
    public void setup(){
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        curatorFramework =
                CuratorFrameworkFactory.newClient(
                        url,
                        5000,
                        3000,
                        retryPolicy);
    }

    @After
    public void teardown(){
        if(this.curatorFramework != null){
            this.curatorFramework.close();
        }
    }
}
