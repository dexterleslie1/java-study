package com.future.demo.java.spring.boot.condition;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author dexterleslie@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={Application.class, ConditionalOnExpressionTests.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ConditionalOnExpressionTests {
    private final static Logger log = LoggerFactory.getLogger(ConditionalOnExpressionTests.class);

    @Autowired
    TestService1 testService1 = null;

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnExpression("${spring.boot.condition.on.expression.test-service1-one:false}")
    TestService1 testService1One() {
        TestService1 service = new TestService1() {
            @Override
            public void sayHello() {
                log.info("testService1One sayHello.");
            }
        };
        return service;
    }

    /**
     *
     * @return
     */
    @Bean
    @ConditionalOnExpression("${spring.boot.condition.on.expression.test-service1-two:false}")
    TestService1 testService1Two() {
        TestService1 service = new TestService1() {
            @Override
            public void sayHello() {
                log.info("testService1Two sayHello.");
            }
        };
        return service;
    }

    /**
     *
     */
    @Test
    public void test() {
       testService1.sayHello();
    }
}
