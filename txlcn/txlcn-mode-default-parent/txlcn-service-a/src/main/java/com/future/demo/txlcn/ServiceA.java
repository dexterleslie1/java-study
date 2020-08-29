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
public class ServiceA {
    @Autowired
    FeignServiceB feignServiceB;
    @Autowired
    DemoRepository demoRepository;
    @Autowired
    RestTemplate restTemplate;

    /**
     *
     * @return
     */
    @LcnTransaction
    public String feignTest1(boolean thrown) {
        Demo demo = new Demo();
        demo.setData("service-a-feign-test1");
        demo.setCreateTime(new Date());
        this.demoRepository.save(demo);

        String result = feignServiceB.feignTest1(thrown);
        return result;
    }

    /**
     *
     * @return
     */
    @LcnTransaction
    public String restTemplateTest1(boolean thrown) {
        Demo demo = new Demo();
        demo.setData("service-a-restTemplate-test1");
        demo.setCreateTime(new Date());
        this.demoRepository.save(demo);

        return this.restTemplate.postForObject("http://txlcn-service-b/api/v1/b/restTemplate/test1?thrown=" + thrown, null, String.class);
    }
}
