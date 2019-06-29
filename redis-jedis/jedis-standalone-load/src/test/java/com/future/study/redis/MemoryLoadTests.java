package com.future.study.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Dexterleslie.Chan
 */
public class MemoryLoadTests {
    private ExecutorService executorService= Executors.newCachedThreadPool();
    @Test
    public void test1() throws InterruptedException {
        for(int i=0;i<100;i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis=null;
                    try{
                        jedis=JedisUtil.getInstance().getJedis();
                        for(int i=0;i<1000000;i++) {
                            String uuid= UUID.randomUUID().toString();
                            jedis.set(uuid, uuid);
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                //
                            }
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }finally{
                        JedisUtil.getInstance().returnJedis(jedis);
                    }
                }
            });
        }
        executorService.shutdown();
        while(!executorService.awaitTermination(1, TimeUnit.SECONDS));
    }
}
