>编译并生成docker镜像
```shell script
mvn package
```

>编译但不生成docker镜像
```shell script
mvn package -Ddockerfile.skip=true
```

前台一键启动所有服务使用docker compose up
```shell script
docker compose up
```

官方参考
https://github.com/spotify/dockerfile-maven

dockerfile-maven-plugin用法参考
https://www.cnblogs.com/Naylor/p/13803532.html

docker-compose.yml设置环境变量
https://www.cnblogs.com/sparkdev/p/9826520.html

多maven模块dockerfile-maven-plugin用法
https://github.com/spotify/dockerfile-maven/issues/153