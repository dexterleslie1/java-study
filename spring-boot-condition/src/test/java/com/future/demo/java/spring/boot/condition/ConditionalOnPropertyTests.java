package com.future.demo.java.spring.boot.condition;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dexterleslie@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={Application.class, ConditionalOnPropertyTests.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ConditionalOnPropertyTests {
    private final static Logger log = LoggerFactory.getLogger(ConditionalOnPropertyTests.class);

    @Autowired(required = false)
    TestService1 testService1 = null;

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(
            value = "spring.boot.condition.need.test-service1",
            havingValue = "true")
    TestService1 testService1() {
        TestService1 service = new TestService1();
        return service;
    }

    /**
     *
     */
    @Test
    public void test() {
       log.info("演示ConditionalOnProperty注解使用，" +
               "application.properties配置spring.boot.condition.need.test-service1=true" +
               "才创建testService1");
       if(testService1==null) {
           log.info("testService1 is null");
       } else {
           log.info("testService1 is not null");
       }
    }
}
