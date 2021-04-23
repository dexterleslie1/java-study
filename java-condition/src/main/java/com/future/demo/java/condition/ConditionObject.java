package com.future.demo.java.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionObject {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void waitFor() {
        this.waitFor(-1);
    }

    public boolean waitFor(long milliseconds) {
        boolean timeout = false;
        lock.lock();
        try {

            if(milliseconds<=0) {
                condition.await();
            } else {
                timeout = !condition.await(milliseconds, TimeUnit.MILLISECONDS);
            }
        }catch (Exception e) {
            //
        }finally {
            lock.unlock();
        }
        return timeout;
    }

    public void signal() {
        lock.lock();
        try {
            condition.signal();
        }catch (Exception e){
            //
        }finally {
            lock.unlock();
        }
    }
}
