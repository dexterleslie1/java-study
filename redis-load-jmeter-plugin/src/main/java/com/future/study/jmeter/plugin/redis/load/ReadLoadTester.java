package com.future.study.jmeter.plugin.redis.load;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;

import java.io.IOException;
import java.util.*;

/**
 * @author dexterleslie@gmail.com
 */
public class ReadLoadTester {
    private RedisTemplate redisTemplate = null;
    private ValueOperations<String,String> valueOperations = null;
    private Random random = new Random();
    private final static List<Integer> sex = new ArrayList<Integer>();
    static {
        sex.add(0);
        sex.add(1);
        sex.add(2);
    }
    private int dbSize = 0;

    public void setup() {
//        RedisStandaloneConfiguration configuration=new RedisStandaloneConfiguration();
//        configuration.setHostName("192.168.1.136");
//        configuration.setPort(6379);
//        configuration.setPassword(RedisPassword.of("aa112233"));
//        JedisConnectionFactory connectionFactory=new JedisConnectionFactory(configuration);
        RedisClusterConfiguration configuration = new RedisClusterConfiguration(
                Arrays.asList("192.168.1.130:6379",
                        "192.168.1.131:6379",
                        "192.168.1.132:6379",
                        "192.168.1.133:6379",
                        "192.168.1.134:6379",
                        "192.168.1.135:6379")
        );
        configuration.setPassword(RedisPassword.of("aa112233"));
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(configuration);
        connectionFactory.afterPropertiesSet();
        redisTemplate=new RedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();
        valueOperations = redisTemplate.opsForValue();
        RedisConnection connection = null;
        try {
            connection = connectionFactory.getConnection();
            this.dbSize = connection.dbSize().intValue();
            if(this.dbSize <= 0) {
                this.dbSize = 1;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            if(connection != null){
                connection.close();
            }
            connection = null;
        }
    }

    public void doRead() throws IOException {
        int index = (int)random.nextInt(this.dbSize);
        String json = valueOperations.get(String.valueOf(index+1));
        if(!StringUtils.isBlank(json)){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.readValue(json, User.class);
        }
    }

    public void teardown() {
    }
}
