package com.future.study.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Dexterleslie.Chan
 */
public class CpuLoadTests {
    private Random random = new Random();
    @Test
    public void test1() throws InterruptedException {
        int looperOutter = 200;
        int looperInner = 2000;
        ExecutorService executorService= Executors.newCachedThreadPool();
        for(int i=0;i<looperOutter;i++) {
            final int j = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis=null;
                    try{
                        jedis=JedisUtil.getInstance().getJedis();
                        for(int i=0;i<looperInner;i++) {
                            String string1 = String.valueOf(j*looperInner+i);
                            jedis.set(string1, string1);
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
        System.out.println("redis缓存数据已准备好，准备进行读效率测试");

        final int max = looperOutter*looperInner;
        executorService= Executors.newCachedThreadPool();
        for(int i=0;i<250;i++) {
            final int j = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Jedis jedis=null;
                    try{
                        jedis=JedisUtil.getInstance().getJedis();
                        for(int i=0;i<10000;i++) {
                            int intTemp = random.nextInt(max);
                            jedis.get(String.valueOf(intTemp));
//                            try {
//                                int milliseconds = random.nextInt(10);
//                                if(milliseconds <=0){
//                                    milliseconds = 1;
//                                }
//                                Thread.sleep(milliseconds);
//                            } catch (InterruptedException e) {
//                                //
//                            }
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
