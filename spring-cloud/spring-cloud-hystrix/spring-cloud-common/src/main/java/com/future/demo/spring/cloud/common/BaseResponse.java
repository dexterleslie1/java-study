package com.future.demo.spring.cloud.common;

import io.swagger.annotations.ApiModelProperty;

/**
 *
 */
public abstract class BaseResponse {
    @ApiModelProperty(value = "错误代码")
    private int errorCode = 0;
    @ApiModelProperty(value = "错误信息")
    private String errorMessage = null;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
