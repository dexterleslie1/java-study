package com.future.demo.spring.cloud.feign.common.entity;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String name;
    private Double price;
}
