package com.future.demo.security.uaa.config;

import com.future.demo.security.uaa.service.ClientDetailsServiceImpl;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
@EnableAuthorizationServer
public class ConfigAuthorizationServer extends AuthorizationServerConfigurerAdapter {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    ClientDetailsServiceImpl clientDetailsService;

    // 使用JwtAccessTokenConverter转换器转jwt token
    @Bean
    TokenStore tokenStore() throws InvalidKeySpecException, NoSuchAlgorithmException {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    JwtAccessTokenConverter jwtAccessTokenConverter() throws NoSuchAlgorithmException, InvalidKeySpecException {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte []publicKeyBytes = Base64.decode("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDPsWeWhr9oydNQYesOrVKLD+Hf\n" +
                "scpOKiakvjX/Oh7V1v1LojTDpK3G8yt7kOHfD7rn3Tdjid46RQ6avHXmPJniqtg9\n" +
                "pqnAMmXqB+9HUcrqxh0k7cL6roD07xuEVNZfQ/MjTfMAJKejR99De3mrG+bCfrxe\n" +
                "kwzIBgOppRSuanjK2wIDAQAB");
        byte []privateKeyBytes = Base64.decode("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAM+xZ5aGv2jJ01Bh\n" +
                "6w6tUosP4d+xyk4qJqS+Nf86HtXW/UuiNMOkrcbzK3uQ4d8PuufdN2OJ3jpFDpq8\n" +
                "deY8meKq2D2mqcAyZeoH70dRyurGHSTtwvqugPTvG4RU1l9D8yNN8wAkp6NH30N7\n" +
                "easb5sJ+vF6TDMgGA6mlFK5qeMrbAgMBAAECgYAqg27n0gdGROHbd1+tLm9SBds/\n" +
                "dd4qZ9hnKoRVDSmYrhxFKhvQ3Fmx+r6w2XRSu56PramT13nExbP6mo8rpMX+0DJp\n" +
                "RUVAqfSdEedRXVAwSO92dirBWoBSuYLNNDJ7f1upq3NSSLqcgfo7dVH4Lgl0yZWe\n" +
                "dkqonAFebGha8YK6uQJBAPoBgkPgD2m/yvW4fhBMvzd6kkLt12fRUJC5CZ+x91eq\n" +
                "9YJjpQWpPVkOrAlD5ybZzlwweDL0Pkf/9KZ9oKc7Uy0CQQDUrC/Ra/mBbOjiYmLO\n" +
                "PUaw5OERXKzcSOnQuH4MEmDTvO3JyRF/WK0Ge5stBotWZoh8fx0DFRrgMVh72xjy\n" +
                "qvsnAkEAssEfbf6nppn+uWC3qlnlovpd18MNcGqmK0RSkD+ENcfEEP3EQV73wVSP\n" +
                "R3Soswuq1BnH587hNUPanqxWkRwG5QJASFbZXQ6xK8jz3i1BFo3ZQcpYlCNF2Rgk\n" +
                "EA7xMQH/VYZqC70M6pgrIo1g1wvm0VjHDDHgmG/RWHjwdBCuh7yI+QJBAOLk5wqs\n" +
                "4I/N5RGaOwCJmZBRp+DM8NOye50avbSNxfmg9NU+3svVcfseN/XSu8Kwl7rRoIxA\n" +
                "JqLurr5RMLwwvPo=");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        KeyPair keyPair = new KeyPair(publicKey, privateKey);
        jwtAccessTokenConverter.setKeyPair(keyPair);
        return jwtAccessTokenConverter;
    }

    // 配置客户端详情服务(ClientDetailsService),ClientDetailsService负责查找ClientDetails
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 集成数据库配置客户端详情
        clients.withClientDetails(clientDetailsService);
//        // 客户端详情内存配置
//        clients.inMemory()
//                .withClient("client1")
//                .secret(passwordEncoder.encode(ClientSecret))
////                .resourceIds("resource1")
//                // authorization_code 授权码模式，，跳转到登录页面需要用户登录后并授权后才能够获取token
//                // implicit 静默授权模式，跳转到登录页面需要用户登录后并授权才能够获取token
//                .authorizedGrantTypes("authorization_code", "implicit")
//                .scopes("all")
//                .redirectUris("http://www.baidu.com")
//
//                .and()
//                .withClient("client2")
//                .secret(passwordEncoder.encode(ClientSecret))
////                .resourceIds("resouce1")
//                // password 密码模式，用户提供账号密码后不需要登录授权直接获取token
//                .authorizedGrantTypes("password")
//                .scopes("write")
//
//                .and().withClient("client3")
//                .secret(passwordEncoder.encode(ClientSecret))
////                .resourceIds("resource1")
//                // client_credentails 客户端模式，不需要提供用户账号密码信息即可获取token
//                .authorizedGrantTypes("client_credentials")
//                .scopes("all")
//
//                // order-service客户端
//                .and().withClient("order-service-resource")
//                .secret(passwordEncoder.encode(ClientSecret))
//                .authorizedGrantTypes("client_credentials")
//                .scopes("all");
    }

    // 配置令牌访问端点
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                // 密码模式，用户提供账号密码不需要登录直接校验通过并获取token
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter());
    }

    // 配置授权服务器端点安全
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 对应/oauth/token_key端点
                .tokenKeyAccess("permitAll()");
//                // 访问/oauth/check_token端点允许所有客户端
//                .checkTokenAccess("permitAll()");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
