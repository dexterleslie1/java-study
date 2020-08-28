package com.future.demo.spring.cloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dexterleslie.Chan
 */
@FeignClient(value = "spring-cloud-helloworld")
public interface HelloService {
    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/sayHello", method = RequestMethod.POST)
    String sayHello(@RequestParam(value = "name", defaultValue = "") String name);
}
