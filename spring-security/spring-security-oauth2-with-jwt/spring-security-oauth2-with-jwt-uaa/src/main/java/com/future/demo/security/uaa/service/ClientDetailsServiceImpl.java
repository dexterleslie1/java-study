package com.future.demo.security.uaa.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.future.demo.security.uaa.mapper.ClientDetailsMapper;
import com.future.demo.security.uaa.model.ClientDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    ClientDetailsMapper clientDetailsMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        QueryWrapper<ClientDetailsModel> queryWrapper = Wrappers.query();
        queryWrapper.eq("clientId", clientId);
        ClientDetailsModel clientDetails =
                clientDetailsMapper.selectOne(queryWrapper);
        return clientDetails;
    }
}
