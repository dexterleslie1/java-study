package com.xy.demo.redisson.lock;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class RedissonLockTests {
    private final static Logger logger = Logger.getLogger(RedissonLockTests.class);
    private final static java.util.Random Random = new Random();

    private String redisHost = "192.168.1.225";
    private int redisPort = 6379;
    private String redisPassword = "123456";

    private RedissonClient redisson = null;

    @Test
    public void test() throws InterruptedException {
        final String key = "fdjiu34938983r399fj3j";
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i=0; i<10; i++) {
            final int seq = i;
            service.submit(new Runnable() {
                public void run() {
                    int milliseconds = Random.nextInt(500);
                    if(milliseconds<=0) {
                        milliseconds = 5;
                    }
                    try {
                        Thread.sleep(milliseconds);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    RLock lock = redisson.getLock(key);
                    boolean isLocked = false;
                    try {
                        isLocked = lock.tryLock(10, 30000, TimeUnit.MILLISECONDS);
                        // 锁定2秒
                        Thread.sleep(2000);
                        String message = String.format("线程%d 锁获取%s", seq, isLocked?"成功":"失败");
                        logger.info(message);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        if (isLocked) {
                            lock = redisson.getLock(key);
                            if (lock != null) {
                                lock.unlock();
                            }
                        }
                    }
                }
            });
        }
        service.shutdown();
        while(!service.awaitTermination(100, TimeUnit.MILLISECONDS));
    }

    @Test
    public void testIsLocked() throws InterruptedException {
        final String key = "fdjiu34938983r399fj3j";
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i=0; i<10; i++) {
            final int seq = i;
            service.submit(new Runnable() {
                public void run() {
                    int milliseconds = Random.nextInt(500);
                    if(milliseconds<=0) {
                        milliseconds = 5;
                    }
                    try {
                        Thread.sleep(milliseconds);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    RLock lock = redisson.getLock(key);
                    try {
                        lock.tryLock(10, 30000, TimeUnit.MILLISECONDS);
                        // 锁定2秒
                        Thread.sleep(2000);
                        String message = String.format("线程%d 锁获取%s", seq, lock.isLocked()?"成功":"失败");
                        logger.info(message);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        if (lock!=null && lock.isLocked()) {
                            lock.unlock();
                        }
                    }
                }
            });
        }
        service.shutdown();
        while(!service.awaitTermination(100, TimeUnit.MILLISECONDS));
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
