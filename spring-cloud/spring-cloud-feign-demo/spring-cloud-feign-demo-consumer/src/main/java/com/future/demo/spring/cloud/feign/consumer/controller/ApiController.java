package com.future.demo.spring.cloud.feign.consumer.controller;

import com.future.demo.spring.cloud.feign.common.entity.Product;
import com.future.demo.spring.cloud.feign.common.feign.ProductFeign;
import com.future.demo.spring.cloud.feign.common.feign.ProductFeignWithSpecifyUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/external/product")
public class ApiController {
    @Autowired
    ProductFeign productFeign;
    @Autowired
    ProductFeignWithSpecifyUrl productFeignWithSpecifyUrl;

    @GetMapping("{productId}")
    public Product info(@PathVariable("productId") Integer productId) {
        Product product = this.productFeign.info(productId);
        Product product2 = this.productFeignWithSpecifyUrl.info(productId);
        log.info("product2=" + product2);
        return product;
    }

    @GetMapping("get")
    public Product get(@RequestParam(value = "productId", required = false) Integer productId) {
        Product product = this.productFeign.get(productId);
        Product product2 = this.productFeignWithSpecifyUrl.get(productId);
        log.info("product2=" + product2);
        return product;
    }

    @PostMapping("add")
    public String add(Product product) {
        String result = this.productFeign.add("customHeaderValue1", product);
        String result2 = this.productFeignWithSpecifyUrl.add("customHeaderValue1", product);
        log.info("result2=" + result2);
        return result;
    }
}
