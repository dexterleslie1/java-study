package com.future.demo.txlcn;

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
    ServiceA serviceA;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/feign/test1",method = RequestMethod.GET)
    public String feignTest1(@RequestParam(value = "thrown", defaultValue = "false") boolean thrown){
        return this.serviceA.feignTest1(thrown);
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/restTemplate/test1",method = RequestMethod.GET)
    public String restTemplateTest1(@RequestParam(value = "thrown", defaultValue = "false") boolean thrown){
        return this.serviceA.restTemplateTest1(thrown);
    }
}
