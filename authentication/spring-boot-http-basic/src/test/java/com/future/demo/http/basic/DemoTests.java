package com.future.demo.http.basic;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class DemoTests {
    /**
     *
     */
    @Test
    public void test() throws Exception {
        String authHttpBasicUser = System.getenv("http.basic.user");
        String authHttpBasicPassword = System.getenv("http.basic.password");
        String host = "localhost";
        int port = 8080;
        String uri = "/hello";
        String url = "http://" + host + ":" + port + uri;
        String name = "Alice";

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("name", name);


        HttpHeaders headers = new HttpHeaders() {
            {
                String auth = authHttpBasicUser + ":" + authHttpBasicPassword;
                byte[] originAuth = auth.getBytes(Charset.forName("utf-8"));
                byte[] encodedAuth = Base64.encodeBase64(originAuth);
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
        MultiValueMap<String, String> multiValueParams= new LinkedMultiValueMap<>();
        if(parameters != null) {
            for(String key : parameters.keySet()) {
                multiValueParams.add(key, parameters.get(key));
            }
        }
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueParams, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<String>(){});

        HttpStatus status = responseEntity.getStatusCode();
        if(status!=HttpStatus.OK) {
            throw new Exception("请求错误，状态 " + responseEntity.getStatusCode());
        }

        Assert.assertEquals("Hello " + name, responseEntity.getBody());
    }
}
