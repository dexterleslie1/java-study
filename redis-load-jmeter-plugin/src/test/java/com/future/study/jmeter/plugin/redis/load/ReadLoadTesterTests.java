package com.future.study.jmeter.plugin.redis.load;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * @author dexterleslie@gmail.com
 */
public class ReadLoadTesterTests {
    private ReadLoadTester tester = null;

    @Before
    public void setup() throws SQLException {
        this.tester = new ReadLoadTester();
        this.tester.setup();
    }

    @After
    public void teardown() throws SQLException {
        if(this.tester != null){
            this.tester.teardown();
        }
    }

    @Test
    public void doRead() throws JsonProcessingException {
        for(int i = 0; i < 100; i++){
            this.tester.doRead();
        }
    }
}
