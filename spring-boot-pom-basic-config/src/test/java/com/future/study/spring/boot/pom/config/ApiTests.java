package com.future.study.spring.boot.pom.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={ApplicationSpringbootPomConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ApiTests {
    @Autowired
    private RestTemplate restTemplate = null;

    @Test
    public void test1(){
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:8080/api/test1",
                String.class);
        Assert.assertEquals("Hello ....", response.getBody());
    }
}
