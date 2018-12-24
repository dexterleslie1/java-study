package com.future.study.jmeter.java.request.plugin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * @author dexterleslie@gmail.com
 */
public class ShardingSphereTesterTests {
    private ShardingSphereTester shardingSphereTester = null;

    @Before
    public void setup() throws SQLException {
        this.shardingSphereTester = new ShardingSphereTester();
        this.shardingSphereTester.setup();
    }

    @After
    public void teardown() throws SQLException {
        if(this.shardingSphereTester != null){
            this.shardingSphereTester.teardown();
        }
    }

    @Test
    public void doInsert(){
        for(int i = 0; i < 100; i++){
            this.shardingSphereTester.doInsert();
        }
    }
}
