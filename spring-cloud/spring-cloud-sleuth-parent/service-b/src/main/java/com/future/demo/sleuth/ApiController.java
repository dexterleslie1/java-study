package com.future.demo.sleuth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@Slf4j
@RestController
public class ApiController {
    @Autowired
    FeignServiceC feignServiceC;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/b/test1",method = RequestMethod.POST)
    public String test1(@RequestParam(value = "name", defaultValue = "") String name){
        log.info("Message from service-b called with parameter name=" + name);
        String result = feignServiceC.test1(name);
        return result;
    }
}
