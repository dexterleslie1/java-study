package com.future.demo.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.future.demo.product.mapper")
public class ApplicationProductService {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationProductService.class, args);
    }
}
