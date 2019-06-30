package com.future.study.jedis.cluster;

import org.junit.After;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Dexterleslie.Chan
 */
public class JedisClusterCpuLoadTests {
    private Random random = new Random();
    @Test
    public void test1() throws InterruptedException {
        int looperOutter = 100;
        int looperInner = 2000;
        ExecutorService executorService= Executors.newCachedThreadPool();
        for(int i=0;i<looperOutter;i++) {
            final int j = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    JedisCluster jedis=null;
                    try{
                        jedis= JedisUtil.getInstance().getJedis();
                        for(int i=0;i<looperInner;i++) {
                            String string1 = String.valueOf(j*looperInner+i);
                            jedis.set(string1, string1);
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }finally{
//                        JedisUtil.getInstance().returnJedis(jedis);
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
                    JedisCluster jedis=null;
                    try{
                        jedis=JedisUtil.getInstance().getJedis();
                        for(int i=0;i<10000;i++) {
                            int intTemp = random.nextInt(max);
                            jedis.get(String.valueOf(intTemp));
                        }
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }finally{
//                        JedisUtil.getInstance().returnJedis(jedis);
                    }
                }
            });
        }
        executorService.shutdown();
        while(!executorService.awaitTermination(1, TimeUnit.SECONDS));
    }

    @After
    public void teardown() throws IOException {
        JedisCluster jedisCluster = JedisUtil.getInstance().getJedis();
        if(jedisCluster != null){
            jedisCluster.close();
        }
    }
}
