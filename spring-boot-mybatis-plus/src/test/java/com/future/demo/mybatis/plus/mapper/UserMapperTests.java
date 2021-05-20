package com.future.demo.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.future.demo.mybatis.plus.Application;
import com.future.demo.mybatis.plus.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
    }

    @Test
    public void testInsert() {
        // insert前总记录数
        int countInsertBefore = userMapper.selectCount(null);
        User user = new User();
        user.setId(10001l);
        user.setAge(30);
        user.setName("Dexterleslie");
        user.setEmail("dexterleslie@gmail.com");
        userMapper.insert(user);

        int countInsertAfter = userMapper.selectCount(null);

        // 删除上面插入数据
        userMapper.deleteById(10001l);

        Assert.assertEquals(countInsertBefore+1, countInsertAfter);
    }
}