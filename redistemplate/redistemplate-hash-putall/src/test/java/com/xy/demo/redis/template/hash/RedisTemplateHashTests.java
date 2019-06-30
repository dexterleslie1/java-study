package com.xy.demo.redis.template.hash;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationRedisTemplateHash.class})
public class RedisTemplateHashTests {
    private final static Random random = new Random();

    @Autowired
    private RedisTemplate redisTemplate = null;

    @Test
    public void testHashPutAllAndMulGet() throws InterruptedException {
        int size = 10;
        Map<Integer, Map<String,Object>> mapIdToZD=new HashMap<Integer,Map<String,Object>>();
        List<Integer> ids = new ArrayList<>();
        for(int i=1; i<=size; i++){
            Map<String,Object> mapZD=new HashMap<String,Object>();
            mapZD.put("id", i);
            mapZD.put("winResult", i);
            mapZD.put("win", i*10);
            mapIdToZD.put(i, mapZD);
            ids.add(i);
        }
        redisTemplate.opsForHash().putAll("cacheGameZD",mapIdToZD);

        List<Map<String,Object>> listReturn=redisTemplate.opsForHash().multiGet("cacheGameZD", ids);
        Assert.assertEquals(size, listReturn.size());
    }
}
