package com.future.demo.sonatype.nexus.reference;

import com.future.demo.sonatype.nexus.distribution.CommonUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class Tests {
    /**
     *
     */
    @Test
    public void test() {
        String string = CommonUtils.method1();
        Assert.assertEquals("This is string from method1", string);
    }
}
