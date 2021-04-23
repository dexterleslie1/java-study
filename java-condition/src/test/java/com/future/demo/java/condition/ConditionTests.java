package com.future.demo.java.condition;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConditionTests {
    private final static Logger log = LoggerFactory.getLogger(ConditionTests.class);

    @Test
    public void test() {
        final ConditionObject conditionObject = new ConditionObject();
        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    conditionObject.signal();
                    log.debug(Thread.currentThread().getName() + " 发出信号");
                } catch (InterruptedException e) {
                    //
                }
            }
        });
        thread.start();

        log.debug(Thread.currentThread().getName() + " 开始等待信号");
        boolean timeout = conditionObject.waitFor(1000);
        if(timeout) {
            log.debug(Thread.currentThread().getName() + " 结束等待，等待超时");
        } else {
            log.debug(Thread.currentThread().getName() + " 结束等待，获取信号");
        }
    }
}
