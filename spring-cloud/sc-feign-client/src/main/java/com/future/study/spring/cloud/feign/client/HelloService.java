package com.future.study.spring.cloud.feign.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

/**
 * @author Dexterleslie.Chan
 */
@FeignClient(value = "SERVICE-HELLOWORLD")
public interface HelloService {
    @RequestMapping(value = "/",method = RequestMethod.GET)
    String sayHello();
}
