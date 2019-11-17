package com.future.study.spring.boot.package1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dexterleslie@gmail.com
 */
public class Tester1 {
    private final static Logger logger = LoggerFactory.getLogger(Tester1.class);

    public void method(){
        logger.debug("Tester1 method is called");
    }
}
