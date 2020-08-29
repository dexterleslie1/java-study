package com.future.demo.txlcn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
public class ApiController {
    @Autowired
    ServiceC serviceC;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/c/feign/test1",method = RequestMethod.POST)
    public String feignTest1() throws Exception {
        return serviceC.feignTest1();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/c/restTemplate/test1",method = RequestMethod.POST)
    public String restTemplateTest() throws Exception {
        return serviceC.restTemplateTest1();
    }
}
