package com.future.study.jmeter.load.zookeeper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * @author dexterleslie@gmail.com
 */
public class ZookeeperLockLoadTesterTests {
    private ZookeeperLockLoadTester tester = null;

    @Before
    public void setup() throws SQLException {
        this.tester = new ZookeeperLockLoadTester();
        this.tester.setup();
    }

    @After
    public void teardown() throws SQLException {
        if(this.tester != null){
            this.tester.teardown();
        }
    }

    @Test
    public void doLock() throws Exception {
        for(int i = 0; i < 100; i++){
            this.tester.doLock();
        }
    }
}
