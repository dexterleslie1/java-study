package com.future.study.spring.boot.package2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dexterleslie@gmail.com
 */
public class Tester2 {
    private final static Logger logger = LoggerFactory.getLogger(Tester2.class);

    public void method(){
        logger.debug("Tester2 method is called");
    }
}
