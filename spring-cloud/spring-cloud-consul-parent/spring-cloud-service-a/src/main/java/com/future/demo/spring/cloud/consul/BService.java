package com.future.demo.spring.cloud.consul;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "spring-cloud-service-b")
public interface BService {
    /**
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/api/v1/b/sayHello", method = RequestMethod.POST)
    public String sayHello(@RequestParam(value = "name", defaultValue = "") String name);
}
