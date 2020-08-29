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
    ServiceB serviceB;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/b/feign/test1",method = RequestMethod.POST)
    public String feignTest1(@RequestParam(value = "thrown", defaultValue = "false") boolean thrown) throws Exception {
        return serviceB.feignTest1(thrown);
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/b/restTemplate/test1",method = RequestMethod.POST)
    public String restTemplateTest1(@RequestParam(value = "thrown", defaultValue = "false") boolean thrown) throws Exception {
        return serviceB.restTemplateTest1(thrown);
    }
}
