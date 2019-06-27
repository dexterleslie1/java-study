package com.xy.demo.redis.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 */
@Configuration
public class RedisConfiguration {
    /**
     *
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel("192.168.1.111", 26381)
                .sentinel("192.168.1.111", 26380)
                .sentinel("192.168.1.111", 26379);
//        RedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(sentinelConfig);
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(sentinelConfig);
        return connectionFactory;
    }

    /**
     *
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
