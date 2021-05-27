package com.future.demo.common.feign;

import com.future.demo.common.entity.ProductEntity;
import com.yyd.common.http.response.ListResponse;
import com.yyd.common.http.response.ObjectResponse;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.*;

@EnableFeignClients(value = "docker-automation-product-service")
@RequestMapping("/api/v1/product")
public interface ProductFeignClient {
    @GetMapping("list")
    ListResponse<ProductEntity> list();

    @PostMapping("add")
    ObjectResponse<String> add(@RequestParam(value = "name", defaultValue = "") String name);

    @DeleteMapping("delete")
    ObjectResponse<String> delete(@RequestParam(value = "id", defaultValue = "0") long id);
}
