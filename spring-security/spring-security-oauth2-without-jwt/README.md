演示spring-security oauth2没有集成jwt

spring-oauth2 授权服务和资源服务配置
https://buddhiprabhath.medium.com/spring-boot-oauth-2-0-separating-authorization-service-and-resource-service-1641ebced1f0

1、authorization_code授权码模式
1.1、请求获取授权码
GET http://localhost:9999/oauth/authorize?response_type=code&client_id=client1
1.2、跳转到百度并获取授权码，使用授权码通过以下post请求获取token
请求参数：grant_type=authorization_code、code=授权码
请求头参数：Authorization=Basic xxxxxxxx(客户端id+客户端secret)
POST http://localhost:9999/oauth/token

2、implicit静默授权模式
2.1、和授权码模式区别：1、不需要获取授权码 2、不需要提供客户端secret，直接通过url hash部分获取token
GET http://localhost:9999/oauth/authorize?response_type=token&client_id=client1

3、password密码模式
3.1、直接返回token
请求参数：grant_type=password、username=xxxx、password=xxxx
请求头参数：Authorization=Basic xxxxxxxx(客户端id+客户端secret)
POST http://localhost:9999/oauth/token

4、client_credentails客户端模式
4.1、直接返回token
请求参数：grant_type=client_credentials
请求头参数：Authorization=Basic xxxxxxxx(客户端id+客户端secret)
POST http://localhost:9999/oauth/token

/oauth/check_token端点
POST http://localhost:9999/oauth/check_token
请求参数：token=xxxxxxxxx