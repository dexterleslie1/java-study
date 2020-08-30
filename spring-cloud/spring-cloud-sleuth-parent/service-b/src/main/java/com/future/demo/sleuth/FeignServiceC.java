package com.future.demo.sleuth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Dexterleslie.Chan
 */
@FeignClient(value = "service-c")
public interface FeignServiceC {
    /**
     *
     * @return
     */
    @RequestMapping(value = "/api/v1/c/test1", method = RequestMethod.POST)
    String test1(@RequestParam(value = "name", defaultValue = "") String name);
}
