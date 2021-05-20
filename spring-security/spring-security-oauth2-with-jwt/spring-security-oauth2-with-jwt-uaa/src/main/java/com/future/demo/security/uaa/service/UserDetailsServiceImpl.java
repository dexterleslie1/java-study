package com.future.demo.security.uaa.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.future.demo.security.uaa.mapper.UserMapper;
import com.future.demo.security.uaa.model.UserDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<UserDetailsModel> queryWrapper = Wrappers.query();
        queryWrapper.eq("username", username);
        UserDetailsModel model = this.userMapper.selectOne(queryWrapper);
        return model;
    }
}
