package com.future.study.rabbitmq.spring.amqp.demo;

/**
 * @author Dexterleslie.Chan
 */
public interface MessageListenerDelegate {
    public void onMessage(byte []datas);
}
