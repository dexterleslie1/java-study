package com.future.demo.spring.boot.transaction;

public class BusinessException extends Exception {
    /**
     *
     * @param message
     */
    public BusinessException(String message) {
        super(message);
    }
}
