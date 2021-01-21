package com.future.demo.spring.cloud.zuul;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class ApiController {
    /**
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/api/v1/sayHello", method = RequestMethod.POST)
    public String sayHello(@RequestParam(value = "name", defaultValue = "") String name) {
        return "Hello " + name + "!!(Zuul)";
    }

    @RequestMapping(value = "/api/v1/sayHello2", method = RequestMethod.POST)
    public String sayHello2(@RequestParam(value = "name", defaultValue = "") String name) {
        return "Hello " + name + "!!(Zuul2)";
    }
}
