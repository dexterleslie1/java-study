package com.future.demo.rest.template;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
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
     * body 参数测试
     */
    @Test
    public void testPostWithBodyMap() {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("name", "Dexter1");
        objectNode.put("age", 11);
        String json = objectNode.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity<>(json, headers);
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:8080/api/v1/postWithBodyMap", HttpMethod.POST, httpEntity, String.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        String response = responseEntity.getBody();
        String message = "你提交的body参数：" + json;
        Assert.assertEquals(message, response);
    }

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
