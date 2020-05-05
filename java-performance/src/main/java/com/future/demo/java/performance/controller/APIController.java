package com.future.demo.java.performance.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author dexterleslie@gmail.com
 */
@RestController
@RequestMapping(value="/api")
public class APIController {
    private final static Map<String, byte[]> MemoryHolderMapper = new HashMap<>();
    
    @Autowired
    private CacheManager cacheManager = null;
    private Cache cache1 = null;
    
    /**
     * 
     */
    @PostConstruct
    public void init() {
    	this.cache1 = this.cacheManager.getCache("cache1");
    }

    /**
     *
     * @return
     */
    @RequestMapping(value="memory/consume", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> memoryConsume(){
    	String key = UUID.randomUUID().toString();
        byte []bytes = new byte[1024*1024*100];
        Random random = new Random();
        random.nextBytes(bytes);
        MemoryHolderMapper.put(key, bytes);
        long totalBytes = 0;
        for(String keyTemporary : MemoryHolderMapper.keySet()) {
        	byte [] bytesTemporary = MemoryHolderMapper.get(keyTemporary);
        	totalBytes += bytesTemporary.length;
        }
        return ResponseEntity.ok("成功分配内存 " + bytes.length/(1024*1024) + "MB，总消耗内存：" + totalBytes/(1024*1024) + "MB");
    }
    
    /**
     * 
     * @return
     */
    @RequestMapping(value="cpu/loaded", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> cpuLoaded(){
    	Random random = new Random();
    	int x = random.nextInt(Integer.MAX_VALUE);
    	int y = random.nextInt(Integer.MAX_VALUE);
    	int z = random.nextInt(Integer.MAX_VALUE);
    	long result = (x-z) * y;
    	String uuid = UUID.randomUUID().toString();
    	Element element = new Element(uuid, result);
    	int seconds = random.nextInt(300);
    	if(seconds<=0) {
    		seconds = 1;
    	}
    	element.setTimeToLive(seconds);
    	this.cache1.put(element);
        return ResponseEntity.ok(String.valueOf(result));
    }
    
    /**
     * 
     * @return
     * @throws InterruptedException 
     */
    @RequestMapping(value="thread/loaded", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> threadLoaded() throws InterruptedException{
    	Random random = new Random();
    	int x = random.nextInt(Integer.MAX_VALUE);
    	int y = random.nextInt(Integer.MAX_VALUE);
    	int z = random.nextInt(Integer.MAX_VALUE);
    	long result = (x-z) * y;
    	String uuid = UUID.randomUUID().toString();
    	Element element = new Element(uuid, result);
    	int seconds = random.nextInt(300);
    	if(seconds<=0) {
    		seconds = 1;
    	}
    	element.setTimeToLive(seconds);
    	this.cache1.put(element);
    	int millis = random.nextInt(5000);
    	if(millis<=0) {
    		millis = 1;
    	}
    	Thread.sleep(millis);
        return ResponseEntity.ok(String.valueOf(result));
    }
}
