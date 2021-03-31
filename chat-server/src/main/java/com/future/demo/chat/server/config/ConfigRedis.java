package com.future.demo.chat.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

/**
 *
 */

@Configuration
public class ConfigRedis {
    @Value("${chatVariableInternalRedisIp}")
    private String redisHost = null;
    @Value("${chatVariableInternalRedisPort}")
    private int redisPort = 0;
    @Value("${chatVariableInternalRedisPassword}")
    private String redisPassword = null;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName(redisHost);
        standaloneConfiguration.setPort(redisPort);
        if(!StringUtils.isEmpty(redisPassword)) {
            standaloneConfiguration.setPassword(RedisPassword.of(redisPassword));
        }
        JedisConnectionFactory jedisConFactory
                = new JedisConnectionFactory(standaloneConfiguration);
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
