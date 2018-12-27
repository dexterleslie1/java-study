package com.future.study.jmeter.load.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 测试结果zookeeper分布式锁单机性能很低 3000MHZ 处理能力为600/s
 * 并且zookeeper分布式锁性能不会随集群节点增加而增加
 * @author dexterleslie@gmail.com
 */
public class ZookeeperLockLoadTester {
    private CuratorFrameworkFactoryBean curatorFrameworkFactoryBean = null;

    public void setup() {
        this.curatorFrameworkFactoryBean = new CuratorFrameworkFactoryBean("192.168.1.131:2181,192.168.1.132:2181,192.168.1.133:2181");
        this.curatorFrameworkFactoryBean.start();
    }

    public void doLock() throws Exception {
        CuratorFramework curatorFramework = this.curatorFrameworkFactoryBean.getObject();
        InterProcessSemaphoreMutex lock=null;
        try{
            String key=UUID.randomUUID().toString();
            lock=new InterProcessSemaphoreMutex(curatorFramework,key);
            if(!lock.acquire(1, TimeUnit.MICROSECONDS)){
                return;
            }
        }catch(Exception ex){
           ex.printStackTrace();
        }finally{
            if(lock!=null&&lock.isAcquiredInThisProcess()){
                lock.release();
                lock=null;
            }
        }
    }

    public void teardown() {
        if(this.curatorFrameworkFactoryBean != null){
            this.curatorFrameworkFactoryBean.stop();
        }
        this.curatorFrameworkFactoryBean = null;
    }
}
