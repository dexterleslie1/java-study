package com.future.demo.spring.cloud.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * 单一对象返回
 * @param <T>
 */
public class ObjectResponse<T> extends BaseResponse{
    @ApiModelProperty(value = "返回数据")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
