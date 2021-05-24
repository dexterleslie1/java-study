package com.future.demo.spring.cloud.feign.consumer.controller;

import com.future.demo.spring.cloud.feign.common.entity.Product;
import com.future.demo.spring.cloud.feign.common.feign.ProductFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/external/product")
public class ApiController {
    @Autowired
    ProductFeign productFeign;

    @GetMapping("{productId}")
    public Product info(@PathVariable("productId") Integer productId) {
        Product product = this.productFeign.info(productId);
        return product;
    }

    @GetMapping("get")
    public Product get(@RequestParam(value = "productId", required = false) Integer productId) {
        Product product = this.productFeign.get(productId);
        return product;
    }

    @PostMapping("add")
    public String add(Product product) {
        String result = this.productFeign.add(product);
        return result;
    }
}
