package com.future.study.jmeter.load.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.SmartLifecycle;
import org.springframework.util.Assert;

/**
 * A spring-friendly way to build a {@link CuratorFramework} and implementing {@link SmartLifecycle}.
 */
public class CuratorFrameworkFactoryBean implements FactoryBean<CuratorFramework>, SmartLifecycle {

    private final Object lifecycleLock = new Object();

    private final CuratorFramework client;

    /**
     * @see SmartLifecycle
     */
    private volatile boolean autoStartup = true;

    /**
     * @see SmartLifecycle
     */
    private volatile boolean running;

    /**
     * @see SmartLifecycle
     */
    private volatile int phase;


    /**
     * Construct an instance using the supplied connection string and using a default
     * retry policy {@code new ExponentialBackoffRetry(1000, 3)}.
     * @param connectionString list of servers to connect to
     */
    public CuratorFrameworkFactoryBean(String connectionString) {
        this(connectionString, new ExponentialBackoffRetry(1000, 3));
    }

    /**
     * Construct an instance using the supplied connection string and retry policy.
     * @param connectionString list of servers to connect to
     * @param retryPolicy      the retry policy
     */
    public CuratorFrameworkFactoryBean(String connectionString, RetryPolicy retryPolicy) {
        Assert.notNull(connectionString, "'connectionString' cannot be null");
        Assert.notNull(retryPolicy, "'retryPolicy' cannot be null");
        this.client = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
    }

    @Override
    public int getPhase() {
        return this.phase;
    }

    /**
     * @param phase the phase
     * @see SmartLifecycle
     */
    public void setPhase(int phase) {
        this.phase = phase;
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public boolean isAutoStartup() {
        return this.autoStartup;
    }

    /**
     * @param autoStartup true to automatically start
     * @see SmartLifecycle
     */
    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    @Override
    public void start() {
        synchronized (this.lifecycleLock) {
            if (!this.running) {
                if (this.client != null) {
                    this.client.start();
                }
                this.running = true;
            }
        }
    }

    @Override
    public void stop() {
        synchronized (this.lifecycleLock) {
            if (this.running) {
                CloseableUtils.closeQuietly(this.client);
                this.running = false;
            }
        }
    }

    @Override
    public void stop(Runnable runnable) {
        stop();
        runnable.run();
    }

    @Override
    public CuratorFramework getObject() throws Exception {
        return this.client;
    }

    @Override
    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}