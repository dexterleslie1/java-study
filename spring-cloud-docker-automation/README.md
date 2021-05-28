> 编译并生成docker镜像
```shell script
mvn package
```

> 编译但不生成docker镜像
```shell script
mvn package -Ddockerfile.skip=true
```

> 拉取镜像(如果本地没有镜像在docker compose up之前需要docker compose pull先拉取远程镜像)
```shell script
docker compose pull
```

> 前台一键启动所有服务使用docker compose up
```shell script
docker compose up
```

> 推送镜像到远程仓库
```shell script
# 生成本地镜像
mvn clean package

# 登录远程仓库
docker login --username=xxxx registry.cn-hangzhou.aliyuncs.com

# 推送本地镜像到远程仓库
mvn dockerfile:push
```

##### 官方参考
https://github.com/spotify/dockerfile-maven

##### dockerfile-maven-plugin用法参考
https://www.cnblogs.com/Naylor/p/13803532.html

##### docker-compose.yml设置环境变量
https://www.cnblogs.com/sparkdev/p/9826520.html

##### 多maven模块dockerfile-maven-plugin用法
https://github.com/spotify/dockerfile-maven/issues/153

##### docker compose传递环境变量
https://docs.docker.com/compose/environment-variables/