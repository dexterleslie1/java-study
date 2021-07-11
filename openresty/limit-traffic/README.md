##### centOS启动openresty docker命令
```shell script
docker run --rm --net=host --name=openresty-demo  -v $(pwd)/default.conf:/etc/nginx/conf.d/default.conf -v $(pwd)/lua:/usr/local/openresty/nginx/conf/lua -v $(pwd)/nginx.conf:/usr/local/openresty/nginx/conf/nginx.conf openresty/openresty
```

##### macOS启动openresty docker命令
```shell script
docker run --rm -p 81:81 -p 80:80 -p 30010:30010 --name=openresty-demo -v $(pwd)/default.conf:/etc/nginx/conf.d/default.conf -v $(pwd)/lua:/usr/local/openresty/nginx/conf/lua -v $(pwd)/nginx.conf:/usr/local/openresty/nginx/conf/nginx.conf openresty/openresty
```