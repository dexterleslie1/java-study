package com.xy.demo.redisson.lock;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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

    /**
     * 演示使用isLocked函数判断key是否被锁定
     * @throws InterruptedException
     */
    @Test
    public void testIsLocked() throws InterruptedException {
        final String key = UUID.randomUUID().toString();
        // 成功获取锁
        final AtomicInteger atomicIntegerAquiredLock = new AtomicInteger();
        // 未成功获取锁
        final AtomicInteger atomicIntegerNotAquiredLock = new AtomicInteger();
        // 已上锁
        final AtomicInteger atomicIntegerLocked = new AtomicInteger();
        // 未上锁
        final AtomicInteger atomicIntegerNotLocked = new AtomicInteger();
        int totalThreads = 100;
        ExecutorService service = Executors.newCachedThreadPool();
        for(int i=0; i<totalThreads; i++) {
            final int seq = i;
            service.submit(new Runnable() {
                public void run() {
                    // 非第一条线程延迟500毫秒，调试isLocked函数
                    if(seq!=0) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            //
                        }
                    }

                    RLock lock = redisson.getLock(key);
                    boolean isLocked = lock.isLocked();
                    if(isLocked) {
                        atomicIntegerLocked.incrementAndGet();
                    } else {
                        atomicIntegerNotLocked.incrementAndGet();
                    }
                    boolean isAquireLock = false;
                    try {
                        isAquireLock = lock.tryLock(10, 30000, TimeUnit.MILLISECONDS);
                        if(isAquireLock) {
                            atomicIntegerAquiredLock.incrementAndGet();
                            // 模拟耗时操作
                            Thread.sleep(2000);
                        } else {
                            atomicIntegerNotAquiredLock.incrementAndGet();
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        if (lock!=null && isAquireLock) {
                            lock.unlock();
                        }
                    }
                }
            });
        }
        service.shutdown();
        while(!service.awaitTermination(100, TimeUnit.MILLISECONDS));

        Assert.assertEquals(1, atomicIntegerAquiredLock.get());
        Assert.assertEquals(totalThreads-1, atomicIntegerNotAquiredLock.get());
        Assert.assertEquals(1, atomicIntegerNotLocked.get());
        Assert.assertEquals(totalThreads-1, atomicIntegerLocked.get());
    }

    /**
     * 测试tryLock函数leaseTime参数
     * @throws InterruptedException
     */
    @Test
    public void testTryLockLeaseTime() throws InterruptedException {
        final AtomicInteger atomicInteger = new AtomicInteger();
        final String key = "keyLease1";
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    RLock rLock = redisson.getLock(key);
                    rLock.tryLock(10, 5000, TimeUnit.MILLISECONDS);
                    Thread.sleep(1000);
                } catch(Exception ex) {

                } finally {

                }
            }
        });

        Thread.sleep(1000);

        executorService.submit(new Runnable() {
            public void run() {
                try {
                    RLock rLock = redisson.getLock(key);
                    while(!rLock.tryLock(10, 20, TimeUnit.MILLISECONDS)) {
                        atomicInteger.incrementAndGet();
                    }
                } catch(Exception ex) {
                } finally {
                    RLock rLock = redisson.getLock(key);
                    rLock.unlock();
                }
            }
        });

        executorService.shutdown();
        while (!executorService.awaitTermination(100, TimeUnit.MILLISECONDS));

        RLock rLock = redisson.getLock(key);
        boolean isLocked = rLock.isLocked();
        Assert.assertFalse(isLocked);
        Assert.assertTrue(atomicInteger.get()>0);
    }

    /**
     * 非本线程unlock锁会抛出IllegalMonitorStateException
     * @throws InterruptedException
     */
    @Test
    public void testUnlockByAnotherThreadWithExceptionExpected() throws InterruptedException, ExecutionException {
        final AtomicInteger atomicIntegerException = new AtomicInteger();
        final AtomicInteger atomicIntegerExceptionCounter = new AtomicInteger();
        final String keyTemp = UUID.randomUUID().toString();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future future = executorService.submit(new Runnable() {
            public void run() {
                try {
                    RLock rLock = redisson.getLock(keyTemp);
                    boolean acquireLock = rLock.tryLock(10, 50000, TimeUnit.MILLISECONDS);
                    Assert.assertTrue(acquireLock);
                    boolean isHeldByCurrentThread = rLock.isHeldByCurrentThread();
                    Assert.assertTrue(isHeldByCurrentThread);
                    boolean isLocked = rLock.isLocked();
                    Assert.assertTrue(isLocked);
                    Thread.sleep(3000);
                } catch (Exception ex) {
                    atomicIntegerException.incrementAndGet();
                } finally {
                    RLock rLock = redisson.getLock(keyTemp);
                    rLock.unlock();
                }
            }
        });

        Thread.sleep(100);

        Future future1 = executorService.submit(new Runnable() {
            public void run() {
                try {
                    RLock rLock = redisson.getLock(keyTemp);
                    boolean acquireLock = rLock.tryLock(10, 30000, TimeUnit.MILLISECONDS);
                    Assert.assertFalse(acquireLock);
                } catch (Exception ex) {
                    atomicIntegerException.incrementAndGet();
                } finally {
                    try {
                        RLock rLock = redisson.getLock(keyTemp);
                        boolean isHeldByCurrentThread = rLock.isHeldByCurrentThread();
                        Assert.assertFalse(isHeldByCurrentThread);
                        boolean isLocked = rLock.isLocked();
                        Assert.assertTrue(isLocked);
                        rLock.unlock();
                    } catch (Exception ex) {
                        if(ex instanceof IllegalMonitorStateException) {
                            atomicIntegerExceptionCounter.incrementAndGet();
                        }
                    }
                }
            }
        });

        executorService.shutdown();
        while(!executorService.awaitTermination(10, TimeUnit.MILLISECONDS));
        future.get();
        future1.get();

        Assert.assertTrue("Exception counter="+atomicIntegerExceptionCounter.get(), atomicIntegerExceptionCounter.get()>0);
        Assert.assertTrue("Exception count="+atomicIntegerException.get(), atomicIntegerException.get()<=0);
    }

    /**
     * 测试isHeldByCurrentThread方法
     */
    @Test
    public void testIsHeldByCurrentThread() throws InterruptedException {
        final AtomicInteger atomicIntegerOwner = new AtomicInteger();
        final AtomicInteger atomicIntegerNotOwner = new AtomicInteger();

        final String keyTemp = UUID.randomUUID().toString();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    RLock rLock = redisson.getLock(keyTemp);
                    rLock.tryLock(10, 5000, TimeUnit.MILLISECONDS);
                    Thread.sleep(4000);
                    boolean isHeldByCurrentThread = rLock.isHeldByCurrentThread();
                    if(isHeldByCurrentThread) {
                        atomicIntegerOwner.incrementAndGet();
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                } finally {
                    RLock rLock = redisson.getLock(keyTemp);
                    rLock.unlock();
                }
            }
        });

        Thread.sleep(500);

        int notOwnerCounter = 15;
        for(int i=0; i<notOwnerCounter; i++) {
            executorService.submit(new Runnable() {
                public void run() {
                    try {
                        RLock rLock = redisson.getLock(keyTemp);
                        rLock.tryLock(10, 5000, TimeUnit.MILLISECONDS);
                        boolean isHeldByCurrentThread = rLock.isHeldByCurrentThread();
                        if(!isHeldByCurrentThread) {
                            atomicIntegerNotOwner.incrementAndGet();
                        }
                    } catch (Exception ex) {

                    } finally {

                    }
                }
            });
        }

        executorService.shutdown();
        while(!executorService.awaitTermination(100, TimeUnit.MILLISECONDS));

        Assert.assertEquals(1, atomicIntegerOwner.get());
        Assert.assertEquals(notOwnerCounter, atomicIntegerNotOwner.get());
    }

    /**
     * 测试当线程结束是否自动释放锁
     */
    @Test
    public void testIfUnlockAutomaticallyWhenThreadEnd() throws InterruptedException {
        final String keyTemp = "keyTestUnlock";
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            public void run() {
                try {
                    RLock rLock = redisson.getLock(keyTemp);
                    rLock.tryLock(10, 10000, TimeUnit.MILLISECONDS);
                } catch (Exception ex) {

                }
            }
        });

        executorService.shutdown();
        while(!executorService.awaitTermination(10, TimeUnit.MILLISECONDS));

        RLock rLock = redisson.getLock(keyTemp);
        boolean isLocked = rLock.isLocked();
        // 验证不会自动释放锁
        Assert.assertTrue(isLocked);
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
