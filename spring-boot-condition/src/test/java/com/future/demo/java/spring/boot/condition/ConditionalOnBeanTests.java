package com.future.demo.java.spring.boot.condition;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dexterleslie@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={Application.class, ConditionalOnBeanTests.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ConditionalOnBeanTests {
    private final static Logger log = LoggerFactory.getLogger(ConditionalOnBeanTests.class);

    @Autowired(required = false)
    TestService2 testService2 = null;

    /**
     *
     * @return
     */
    @Bean
    TestService1 testService1() {
        TestService1 service = new TestService1();
        return service;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnBean(name = "testService1")
    TestService2 testService2() {
        TestService2 service = new TestService2();
        return service;
    }

    /**
     *
     */
    @Test
    public void test() {
        log.info("演示ConditionalOnBean注解使用，" +
                "尝试注释和取消注释TestService1 @Bean配置，" +
                "两种情况输出结果不相同");
        if(testService2==null) {
            log.info("testService2 is null");
        } else {
            log.info("testService2 is not null");
        }
    }
}
