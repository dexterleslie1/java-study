package com.future.demo.spring.cloud.ribbon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@RestController
public class ApiController {
    @Autowired
    RestTemplate restTemplate;

    /**
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/api/v1/external/sayHello", method = RequestMethod.GET)
    public String sayHello(@RequestParam(value = "name", defaultValue = "") String name) {
        Map<String,String> params=new HashMap<>();
        params.put("name", name);
        return restTemplate.postForObject("http://spring-cloud-helloworld/api/v1/sayHello",params, String.class);
    }
}
