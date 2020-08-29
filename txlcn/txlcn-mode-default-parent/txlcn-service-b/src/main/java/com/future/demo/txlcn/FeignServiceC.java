package com.future.demo.txlcn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dexterleslie.Chan
 */
@FeignClient(value = "txlcn-service-c")
public interface FeignServiceC {
    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/c/feign/test1", method = RequestMethod.POST)
    String feignTest1();
}
