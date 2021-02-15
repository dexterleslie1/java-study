package com.future.demo.rest.template;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(
        classes={Application.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public class RestTemplateTests {
    @Autowired
    private RestTemplate restTemplate = null;

    /**
     * 头参数
     */
    @Test
    public void testPostWithHeaders(){
        String name = "Dexter1";
        String token = "f1447ac1-f007-49c1-a7c3-d9ad3637c60e";
        MultiValueMap<String, String> multiValueParams= new LinkedMultiValueMap<>();
        multiValueParams.add("name", name);
        MultiValueMap<String, String> multiValueHeaders= new LinkedMultiValueMap<>();
        multiValueHeaders.add("token", token);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueParams, multiValueHeaders);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:8080/api/v1/postWithHeaders", HttpMethod.POST, httpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String response = responseEntity.getBody();
        String message = "你提交的参数 name=" + name + ",token=" + token;
        Assert.assertEquals(message, response);
    }

    /**
     *
     */
    @Test
    public void testPostWithoutParameters(){
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:8080/api/v1/postWithoutParameters", HttpMethod.POST, null, String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String response = responseEntity.getBody();
        Assert.assertEquals("没有任何参数提交", response);
    }
}
