package com.future.demo.txlcn;

import com.codingapi.txlcn.tc.annotation.LcnTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 */
@Service
public class ServiceC {
    @Autowired
    DemoRepository demoRepository;

    /**
     *
     * @return
     */
    @LcnTransaction
    public String feignTest1() throws Exception {
        Demo demo = new Demo();
        demo.setData("service-c-feign-test1");
        demo.setCreateTime(new Date());
        this.demoRepository.save(demo);

        return "Return message from service-c-feign-test1 method";
    }

    /**
     *
     * @return
     */
    @LcnTransaction
    public String restTemplateTest1() throws Exception {
        Demo demo = new Demo();
        demo.setData("service-c-restTemplate-test1");
        demo.setCreateTime(new Date());
        this.demoRepository.save(demo);

        return "Return message from service-c-restTemplate-test1 method";
    }
}
