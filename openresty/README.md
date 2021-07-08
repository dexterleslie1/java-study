演示openresty lua编程

docker运行openresty容器
docker run --rm --name=openresty-demo --net=host -v $(pwd)/nginx-getting-started.conf:/usr/local/openresty/nginx/conf/nginx.conf openresty/openresty
