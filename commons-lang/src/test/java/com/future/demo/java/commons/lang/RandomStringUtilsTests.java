package com.future.demo.java.commons.lang;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class RandomStringUtilsTests {
    /**
     *
     */
    @Test
    public void random() {
        int length = 10;
        String randomString = RandomStringUtils.random(length);
        Assert.assertEquals(length, randomString.length());
    }

    /**
     *
     */
    @Test
    public void randomAlphabetic() {
        int length = 10;
        String randomString = RandomStringUtils.randomAlphabetic(length);
        Assert.assertEquals(length, randomString.length());
    }

    /**
     *
     */
    @Test
    public void randomAlphanumeric() {
        int length = 10;
        String randomString = RandomStringUtils.randomAlphanumeric(length);
        Assert.assertEquals(length, randomString.length());
    }

    /**
     *
     */
    @Test
    public void randomNumeric() {
        int length = 10;
        String randomString = RandomStringUtils.randomNumeric(length);
        Assert.assertEquals(length, randomString.length());
    }

    /**
     *
     */
    @Test
    public void randomGraph() {
        int length = 10;
        String randomString = RandomStringUtils.randomGraph(length);
        Assert.assertEquals(length, randomString.length());
    }
}
