package com.future.demo.mybatis.plus.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user")
public class User {
    @TableId(value = "id")
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
