package com.future.study.jmeter.plugin.redis.load;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.TransportMode;

import java.util.concurrent.TimeUnit;

/**
 * @author dexterleslie@gmail.com
 */
public class RedissonManager {
    private static RedissonClient redisson = null;

    static{
        if(redisson == null) {
            Config config = new Config();
            /*config = config.setCodec(new StringCodec()).setTransportMode(TransportMode.NIO);*/
            config.useClusterServers().setPassword("aa112233").addNodeAddress(
                    "redis://192.168.1.130:6379", "redis://192.168.1.131:6379", "redis://192.168.1.132:6379",
                    "redis://192.168.1.133:6379", "redis://192.168.1.134:6379", "redis://192.168.1.135:6379"
            )/*.setConnectTimeout(100000)
                    .setIdleConnectionTimeout(100000)
                    .setTimeout(100000)
                    .setReadMode(ReadMode.MASTER_SLAVE)
                    .setRetryAttempts(1)*/;
//            String redisConnectionString = "redis://192.168.1.136:6379";
//            Config config = new Config();
//            config.useSingleServer().setPassword("aa112233").setAddress(redisConnectionString);
            redisson = Redisson.create(config);
        }
    }

    /**
     *
     * @param key
     * @throws InterruptedException
     */
    public static void tryLock(String key) throws InterruptedException {
        RLock mylock = redisson.getLock(key);
        mylock.tryLock(10, 10000, TimeUnit.MILLISECONDS);
    }

    /**
     *
     * @param key
     */
    public static void unlock(String key){
        RLock mylock = redisson.getLock(key);
        if(mylock != null) {
            mylock.unlock();
        }
    }

//    /**
//     *
//     */
//    public static void shutdown(){
//        if(redisson != null) {
//            redisson.shutdown();
//        }
//        redisson = null;
//    }
}
