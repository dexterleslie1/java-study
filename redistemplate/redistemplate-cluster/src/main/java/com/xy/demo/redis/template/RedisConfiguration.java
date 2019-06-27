package com.xy.demo.redis.template;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
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
        RedisClusterConfiguration config = new RedisClusterConfiguration();
        config.addClusterNode(new RedisNode("192.168.1.111", 6381));
        config.addClusterNode(new RedisNode("192.168.1.111", 6380));
        config.addClusterNode(new RedisNode("192.168.1.111", 6379));
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(config);
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
