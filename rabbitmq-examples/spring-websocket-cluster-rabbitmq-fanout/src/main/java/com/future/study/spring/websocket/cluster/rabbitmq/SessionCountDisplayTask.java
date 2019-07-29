package com.future.study.spring.websocket.cluster.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Dexterleslie.Chan
 */
public class SessionCountDisplayTask {
    private final static Logger logger = LoggerFactory.getLogger(SessionCountDisplayTask.class);

    @Autowired
    private MQHandler mqHandler = null;

    public void process() {
        int size = this.mqHandler.getSessionsRegistry().size();
        logger.info("当前session总数：" + size);
    }
}
