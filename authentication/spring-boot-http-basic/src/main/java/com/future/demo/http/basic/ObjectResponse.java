package com.future.demo.http.basic;

/**
 * 单一对象返回
 * @param <T>
 */
public class ObjectResponse<T> extends BaseResponse{
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
