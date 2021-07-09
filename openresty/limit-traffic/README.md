centOS启动openresty docker命令
docker run --rm --net=host --name=openresty-demo -v $(pwd)/lua:/usr/local/openresty/nginx/conf/lua -v $(pwd)/nginx.conf:/usr/local/openresty/nginx/conf/nginx.conf openresty/openresty

macOS启动openresty docker命令
docker run --rm -p 80:80 -p 30010:30010 --name=openresty-demo -v $(pwd)/lua:/usr/local/openresty/nginx/conf/lua -v $(pwd)/nginx.conf:/usr/local/openresty/nginx/conf/nginx.conf openresty/openresty
