package com.future.demo.spring.cloud.feign.common.feign;

import com.future.demo.spring.cloud.feign.common.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        value = "productFeignWithSpecifyUrl",
        url = "http://localhost:8090",
        path = "/api/v1/product")
public interface ProductFeignWithSpecifyUrl {
    @GetMapping("{productId}")
    Product info(@PathVariable("productId") Integer productId);

    @GetMapping("get")
    Product get(@RequestParam(value = "productId", required = false) Integer productId);

    @PostMapping("add")
    String add(@RequestHeader(value = "customHeader") String customHeader,
               @RequestBody(required = false) Product product);
}
