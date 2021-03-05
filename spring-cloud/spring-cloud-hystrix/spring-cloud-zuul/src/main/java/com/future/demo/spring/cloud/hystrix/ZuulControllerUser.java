package com.future.demo.spring.cloud.hystrix;

import com.future.demo.spring.cloud.common.ObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class ZuulControllerUser {
    @Autowired
    ApiUser apiUser;
    @Autowired
    ApiUserRestTemplate apiUserRestTemplate;

    @PostMapping("timeoutWithFeignFallback")
    ResponseEntity<ObjectResponse<String>> timeoutWithFeignFallback(@RequestParam(value = "milliseconds") Integer milliseconds) {
        return this.apiUser.timeout(milliseconds);
    }

    @PostMapping("timeoutWithRestTemplate")
    ResponseEntity<ObjectResponse<String>> timeoutWithRestTemplate(@RequestParam(value = "milliseconds") Integer milliseconds) {
        return this.apiUserRestTemplate.timeout(milliseconds);
    }
}
