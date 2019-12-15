package com.future.demo.java.spring.boot.condition;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dexterleslie@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={Application.class, ConditionalOnMissingBeanTests.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ConditionalOnMissingBeanTests {
    private final static Logger log = LoggerFactory.getLogger(ConditionalOnMissingBeanTests.class);

    @Autowired
    TestService1 testService1 = null;

    /**
     *
     * @return
     */
    @Bean
    TestService1 testService1One() {
        TestService1 service = new TestService1();
        return service;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "testService1One")
    TestService1 testService1Two() {
        TestService1 service = new TestService1();
        return service;
    }

    /**
     *
     */
    @Test
    public void test() {
       log.info("演示ConditionalOnMissingBean注解使用，" +
               "如果不配置@ConditionalOnMissingBean应用不能启动，" +
               "因为两个重复的TestService1类型bean存在");
    }
}
