package com.xy.future.study.redis.template.list;

import com.xy.future.study.redis.template.list.ApplicationRedisTemplateList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationRedisTemplateList.class})
public class RedisTemplateListTests {
    private final static Random random = new Random();

    @Autowired
    private RedisTemplate redisTemplate = null;

    @Test
    public void test() throws InterruptedException {
        String key = "keyListType";
        this.redisTemplate.delete(key);

        int total = 100;
        // lpush
        for(int i=total-1; i>=0; i--) {
            this.redisTemplate.opsForList().leftPush(key, String.valueOf(i));
        }

        // llen
        long length = this.redisTemplate.opsForList().size(key);
        Assert.assertEquals(total, length);

        // rpop
        for(int i=0; i<total/2; i++) {
            this.redisTemplate.opsForList().rightPop(key);
        }

        length = this.redisTemplate.opsForList().size(key);
        Assert.assertEquals(total/2, length);

        // range
        List<String> values = this.redisTemplate.opsForList().range(key, 0, -1);
        Assert.assertEquals(total/2, values.size());

        // ltrim
        int startIndex = 10;
        int endIndex = 19;
        this.redisTemplate.opsForList().trim(key, startIndex, endIndex);
        length = this.redisTemplate.opsForList().size(key);
        Assert.assertEquals(endIndex-startIndex+1, length);

        List<String> list1 = new ArrayList<>();
        for(int i=startIndex; i<=endIndex; i++) {
            list1.add(String.valueOf(i));
        }
        values = this.redisTemplate.opsForList().range(key, 0, -1);
        Collections.sort(list1);
        Collections.sort(values);
        Assert.assertEquals(list1, values);
        length = this.redisTemplate.opsForList().size(key);
        Assert.assertEquals(endIndex-startIndex+1, length);
    }
}
