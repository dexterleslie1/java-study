package com.future.demo.maven.dockerfile.plugin;

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
        classes={Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class ApiTests {
    @Autowired
    private RestTemplate restTemplate = null;

    @Test
    public void test1(){
        ResponseEntity<String> response = this.restTemplate.getForEntity(
                "http://localhost:8080/",
                String.class);
        Assert.assertEquals("你好，dockerfile-maven-plugin 演示", response.getBody());
    }
}
