package com.future.study.spring.security.passwordencoder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author dexterleslie@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={WebSecurityConfig.class})
public class BCryptPasswordEncoderTests {
    private final static Logger logger = LoggerFactory.getLogger(BCryptPasswordEncoderTests.class);

    /**
     *
     */
    @Autowired
    @Qualifier(value = "bCryptPasswordEncoder")
    private PasswordEncoder passwordEncoder = null;

    /**
     *
     */
    @Test
    public void test() {
        String rawValue = "123456";
        String encodePassword = this.passwordEncoder.encode(rawValue);
        String encodePassword1 = this.passwordEncoder.encode(rawValue);
        Assert.assertNotEquals(encodePassword, encodePassword1);
        boolean match = this.passwordEncoder.matches(rawValue, encodePassword);
        Assert.assertTrue(match);
        match = this.passwordEncoder.matches(rawValue, encodePassword1);
        Assert.assertTrue(match);
    }

    /**
     *
     */
    @Test
    public void test1() {
        for(int i=0; i<100; i++) {
            String uuid = UUID.randomUUID().toString();
            match(uuid);
        }
    }

    void match(String rawValue) {
        String encodePassword = this.passwordEncoder.encode(rawValue);
        String encodePassword1 = this.passwordEncoder.encode(rawValue);
        Assert.assertNotEquals(encodePassword, encodePassword1);
        boolean match = this.passwordEncoder.matches(rawValue, encodePassword);
        Assert.assertTrue(match);
        match = this.passwordEncoder.matches(rawValue, encodePassword1);
        Assert.assertTrue(match);
    }

    /**
     *
     */
    @Test
    public void testPerformance() {
        int count = 100;
        Map<String, String> rawToEncodeMap = new HashMap<>();

        Date timeStart = new Date();
        for(int i=0; i<count; i++) {
            String uuid = UUID.randomUUID().toString();
            String encodePassword = this.passwordEncoder.encode(uuid);
            rawToEncodeMap.put(uuid, encodePassword);
        }
        Date timeEnd = new Date();
        logger.info("编码 {} 个密码耗时 {} 毫秒", count, (timeEnd.getTime() - timeStart.getTime()));

        timeStart = new Date();
        for(String key : rawToEncodeMap.keySet()) {
            String value = rawToEncodeMap.get(key);
            boolean match = this.passwordEncoder.matches(key, value);
            Assert.assertTrue(match);
        }
        timeEnd = new Date();
        logger.info("对比 {} 个密码耗时 {} 毫秒", count, (timeEnd.getTime() - timeStart.getTime()));
    }
}
