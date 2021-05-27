package com.future.demo.product.controller;

import com.future.demo.common.entity.ProductEntity;
import com.future.demo.product.service.ProductService;
import com.yyd.common.http.response.ListResponse;
import com.yyd.common.http.response.ObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ApiController {
    @Autowired
    ProductService productService;

    @GetMapping("list")
    public ListResponse<ProductEntity> list() {
        List<ProductEntity> entityList = this.productService.list();
        ListResponse<ProductEntity> response = new ListResponse<>();
        response.setData(entityList);
        return response;
    }

    @PostMapping("add")
    public ObjectResponse<String> add(
            @RequestParam(value = "name", defaultValue = "") String name) {
        ProductEntity entity = new ProductEntity();
        entity.setName(name);
        entity.setCreateTime(new Date());
        this.productService.save(entity);

        ObjectResponse<String> response = new ObjectResponse<>();
        response.setData("创建成功");
        return response;
    }

    @DeleteMapping("delete")
    public ObjectResponse<String> delete(
            @RequestParam(value = "id", defaultValue = "0") long id) {
        this.productService.removeById(id);
        ObjectResponse<String> response = new ObjectResponse<>();
        response.setData("删除成功");
        return response;
    }
}
