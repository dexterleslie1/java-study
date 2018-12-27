package com.future.study.jmeter.plugin.redis.load;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.TransportMode;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author dexterleslie@gmail.com
 */
public class RedissonLockLoadTester {
    public void setup() {

    }

    public void doLock() {
        String uuid = UUID.randomUUID().toString();
        try {
            RedissonManager.tryLock(uuid);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedissonManager.unlock(uuid);
        }
    }

    public void teardown() {
    }
}
