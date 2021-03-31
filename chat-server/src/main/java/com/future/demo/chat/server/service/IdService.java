package com.future.demo.chat.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class IdService {
    private final static String KeyMessageId = "MessageId";

    @Autowired
    RedisTemplate redisTemplate;

//    @PostConstruct
//    public void init1() {
//        ValueOperations valueOperations = this.redisTemplate.opsForValue();
//        Object value = valueOperations.get(KeyMessageId);
//        if(value==null) {
//            valueOperations.set(KeyMessageId, 1);
//        }
//    }

    public synchronized long get(String key) {
        if(StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("没有提供key");
        }

        ValueOperations valueOperations = this.redisTemplate.opsForValue();
        Long id = valueOperations.increment(key, 1);
        return id;
    }
}
