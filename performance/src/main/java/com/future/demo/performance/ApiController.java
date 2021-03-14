package com.future.demo.performance;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author dexterleslie@gmail.com
 */
@RestController
@RequestMapping(value="/api/v1")
public class ApiController {
	
    @Autowired
    private CacheManager cacheManager = null;
    private Cache cache1 = null;
    private Cache cacheMemoryHolder = null;

	@PostMapping("timeout")
	ResponseEntity<String> timeout(@RequestParam(value = "timeout", defaultValue = "0") Integer timeout) {
		if(timeout>0) {
			try {
				TimeUnit.MILLISECONDS.sleep(timeout);
			} catch (InterruptedException e) {
				//
			}
		}
		return ResponseEntity.ok("成功调用timeout接口");
	}
    
    /**
     * 
     */
    @PostConstruct
    public void init() {
    	this.cache1 = this.cacheManager.getCache("cache1");
    	this.cacheMemoryHolder = this.cacheManager.getCache("cacheMemoryHolder");
    }

    private final static int BytesInMemory = 1024*1024*100;
    /**
     *
     * @return
     */
    @RequestMapping(value="memory/consume", produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> memoryConsume(){
    	String key = UUID.randomUUID().toString();
        byte []bytes = new byte[BytesInMemory];
        Random random = new Random();
        random.nextBytes(bytes);
        Element element = new Element(key, bytes);
        element.setTimeToLive(1);
        this.cacheMemoryHolder.put(element);
        List<String> keys = this.cacheMemoryHolder.getKeys();
        long totalBytes = keys.size()*BytesInMemory;
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
