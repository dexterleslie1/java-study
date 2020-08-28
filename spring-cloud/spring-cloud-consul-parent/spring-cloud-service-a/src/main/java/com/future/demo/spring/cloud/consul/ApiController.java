package com.future.demo.spring.cloud.consul;

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
    BService service;

    /**
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/api/v1/a/sayHello", method = RequestMethod.GET)
    public String sayHello(@RequestParam(value = "name", defaultValue = "") String name) {
        return service.sayHello(name);
    }
}
