package com.future.study.common.logging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

/**
 * @author Dexterleslie.Chan
 */
public class CommonLoggingTests {
    private final static Log logger= LogFactory.getLog(CommonLoggingTests.class);

    @Test
    public void test_log(){
        logger.debug("Debug message");
        logger.error("Error message");
        logger.warn("Warning messsage");
        logger.trace("Trace message");
    }
}
