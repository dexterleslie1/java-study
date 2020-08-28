package com.future.demo.spring.cloud.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class ApiController {
    @Autowired
    HelloService helloService;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/external/sayHello",method = RequestMethod.GET)
    public String sayHello(@RequestParam(value = "name", defaultValue = "") String name){
        String hello = helloService.sayHello(name);
        return hello;
    }
}
