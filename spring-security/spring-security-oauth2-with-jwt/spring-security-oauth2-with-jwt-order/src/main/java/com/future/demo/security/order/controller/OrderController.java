package com.future.demo.security.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @GetMapping(value = "/order")
    @PreAuthorize("hasAuthority('sys:admin')")
    public String order() {
        return "下单成功";
    }
}
