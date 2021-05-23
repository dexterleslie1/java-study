package com.future.demo.config.center.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Value("${customize.args.int}")
    private int customizeInt;
    @Value("${customize.args.string}")
    private String customizeString;

    @GetMapping(value = "/getCustomizeArgs")
    public String getCustomizeArgs(){
        return "配置参数customize.args.int=" + customizeInt + ",customizeString=" + customizeString;
    }
}
