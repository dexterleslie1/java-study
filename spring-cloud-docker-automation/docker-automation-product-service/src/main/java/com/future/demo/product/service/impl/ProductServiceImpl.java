package com.future.demo.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.future.demo.common.entity.ProductEntity;
import com.future.demo.product.mapper.ProductMapper;
import com.future.demo.product.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductEntity> implements ProductService {
}
