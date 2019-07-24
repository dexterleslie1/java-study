package com.future.study.spring.http.trace;

/**
 *
 */
public abstract class BaseResponse {
    private int errorCode = 0;
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
