package com.future.demo.spring.boot.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, TestSupportConfig.class}, properties = "spring.boot.test.demo.testing=true")
public class TestServiceTests {
    @Autowired
    TestService testService;

    @Test
    public void test() {
        int c = this.testService.add(1, 2);
        log.debug("c=" + c);
    }
}
