package com.future.demo.chat.server.common;

import lombok.Data;

import java.util.List;

@Data
public class ListResponse<T> extends BaseResponse {
    private List<T> data;
}
