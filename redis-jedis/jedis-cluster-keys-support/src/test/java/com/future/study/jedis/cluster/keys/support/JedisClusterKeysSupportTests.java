package com.future.study.jedis.cluster.keys.support;

import com.future.study.jedis.cluster.keys.support.JedisUtil;
import org.junit.After;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Dexterleslie.Chan
 */
public class JedisClusterKeysSupportTests {
    private Random random = new Random();
    @Test
    public void test1() throws InterruptedException {
        JedisCluster jedis = JedisUtil.getInstance().getJedis();
        TreeSet<String> keys = new TreeSet<>();
        Map<String, JedisPool> clusterNodes =
                jedis.getClusterNodes();

        for (String key : clusterNodes.keySet()) {
            JedisPool jedisPool = clusterNodes.get(key);
            Jedis jedisConn = jedisPool.getResource();
            try {
                keys.addAll(jedisConn.keys("hello*"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                jedisConn.close();
            }
        }
        System.out.println(keys);

        // 测试以上jedisConn.close()是否会影响到JedisCluster连接池被部分关闭
        ExecutorService executorService= Executors.newCachedThreadPool();
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
                            int intTemp = random.nextInt(Integer.MAX_VALUE);
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
