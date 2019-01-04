package com.future.study.jmeter.java.request.plugin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * @author dexterleslie@gmail.com
 */
public class JDBCReadTesterTests {
    private JDBCReadTester tester = null;

    @Before
    public void setup() throws SQLException, ClassNotFoundException {
        this.tester = new JDBCReadTester();
        this.tester.setup();
    }

    @After
    public void teardown() throws SQLException {
        if(this.tester != null){
            this.tester.teardown();
        }
    }

    @Test
    public void doInsert() throws SQLException {
        for(int i = 0; i < 1000000; i++){
            this.tester.doRead();
        }
    }
}
