package com.future.study.jmeter.java.request.plugin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * @author dexterleslie@gmail.com
 */
public class JDBCWriteTesterTests {
    private JDBCWriteTester tester = null;

    @Before
    public void setup() throws SQLException, ClassNotFoundException {
        this.tester = new JDBCWriteTester();
        this.tester.setup();
    }

    @After
    public void teardown() throws SQLException {
        if(this.tester != null){
            this.tester.teardown();
        }
    }

    @Test
    public void doInsert(){
        for(int i = 0; i < 100; i++){
            this.tester.doInsert();
        }
    }
}
