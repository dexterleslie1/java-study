package com.future.demo.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("docker_automation_product")
public class ProductEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private long id;
    private String name;
    private Date createTime;
}
