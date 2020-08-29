package com.future.demo.txlcn;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 *
 */
@Service
public class ServiceB {
    @Autowired
    FeignServiceC feignServiceC;
    @Autowired
    DemoRepository demoRepository;
    @Autowired
    RestTemplate restTemplate;

    /**
     *
     */
    @LcnTransaction
    public String feignTest1(boolean thrown) throws Exception {
        Demo demo = new Demo();
        demo.setData("service-b-feign-test1");
        demo.setCreateTime(new Date());
        this.demoRepository.save(demo);

        String result = this.feignServiceC.feignTest1();

        if(thrown) {
            throw new Exception("意料中异常");
        }

        return result;
    }

    /**
     *
     * @return
     */
    @LcnTransaction
    public String restTemplateTest1(boolean thrown) throws Exception {
        Demo demo = new Demo();
        demo.setData("service-b-restTemplate-test1");
        demo.setCreateTime(new Date());
        this.demoRepository.save(demo);

        String result = this.restTemplate.postForObject("http://txlcn-service-c/api/v1/c/restTemplate/test1", null, String.class);

        if(thrown) {
            throw new Exception("意料中异常");
        }
        return result;
    }
}
