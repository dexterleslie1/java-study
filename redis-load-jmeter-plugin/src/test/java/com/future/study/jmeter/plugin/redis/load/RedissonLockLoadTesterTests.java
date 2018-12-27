package com.future.study.jmeter.plugin.redis.load;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author dexterleslie@gmail.com
 */
public class RedissonLockLoadTesterTests {
    private RedissonLockLoadTester tester = null;

    @Before
    public void setup() throws SQLException {
        this.tester = new RedissonLockLoadTester();
        this.tester.setup();
    }

    @After
    public void teardown() throws SQLException {
        if(this.tester != null){
            this.tester.teardown();
        }
    }

    @Test
    public void doLock() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int j = 0; j < 50; j++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
//                    RedissonLockLoadTester tester = null;
                    try {
//                        tester = new RedissonLockLoadTester();
//                        tester.setup();
                        for (int i = 0; i < 3000; i++) {
                            tester.doLock();
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }finally{
//                        if(tester != null){
//                            tester.teardown();
//                        }
//                        tester = null;
                    }
                }
            });
        }
        executorService.shutdown();
        while(!executorService.awaitTermination(10, TimeUnit.MILLISECONDS));
    }
}
