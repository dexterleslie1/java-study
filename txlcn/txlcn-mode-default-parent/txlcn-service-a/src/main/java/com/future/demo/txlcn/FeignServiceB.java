package com.future.demo.txlcn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dexterleslie.Chan
 */
@FeignClient(value = "txlcn-service-b")
public interface FeignServiceB {
    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/b/feign/test1", method = RequestMethod.POST)
    String feignTest1(@RequestParam(value = "thrown", defaultValue = "false") boolean thrown);
}
