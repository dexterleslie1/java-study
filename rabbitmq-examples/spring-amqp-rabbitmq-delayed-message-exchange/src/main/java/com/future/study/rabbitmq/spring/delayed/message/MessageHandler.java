package com.future.study.rabbitmq.spring.delayed.message;

/**
 *
 */
public abstract class MessageHandler {
    public abstract void handle(String message);
}
