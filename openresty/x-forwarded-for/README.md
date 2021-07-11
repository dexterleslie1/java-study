# 演示x-forwarded-for、proxy_add_x_forwarded_for用法

##### 参考资料
https://www.runoob.com/w3cnote/http-x-forwarded-for.html

##### 启动业务backend代理
```shell script
docker run --rm --net=host --name=openresty-backend -v $(pwd)/nginx-backend.conf:/usr/local/openresty/nginx/conf/nginx.conf openresty/openresty
```