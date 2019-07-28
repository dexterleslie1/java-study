spring-websocket服务器集群，使用spring-amqp+rabbitmq fanout实现

编译
mvn package

编译镜像
docker build -t test-image1 .
运行镜像，hostip为宿主机ip，用于jmx监控用途
docker run -d --name=test1 -p8080:8080 -p19000:19000 -e "hostip=192.168.1.158" test-image1