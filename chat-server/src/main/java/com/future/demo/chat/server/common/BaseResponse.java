package com.future.demo.chat.server.common;

import lombok.Data;

@Data
public abstract class BaseResponse {
    private int errorCode = 0;
    private String errorMessage = null;
}
