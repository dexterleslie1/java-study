package com.future.study.spring.cloud.ribbon.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Dexterleslie.Chan
 */
@Service
public class HelloService {
    @Autowired
    RestTemplate restTemplate;

    public String getHelloContent() {
        return restTemplate.getForObject("http://SERVICE-HELLOWORLD/",String.class);
    }
}
