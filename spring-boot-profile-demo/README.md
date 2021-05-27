## 演示springboot profile用法

> 使用application-xxx.properities文件命名方式切换profile

> 使用yaml文件块方式切换profile

> profile切换能够通过命令行参数、jvm参数方式实现

##### 命令行参数方式
```shell script
java -jar spring-boot-profile-demo-1.0.0.jar --spring.profiles.active=dev
```

##### jvm参数方式(经过测试不能用于yaml方式切换profile)
```shell script
java -jar spring-boot-profile-demo-1.0.0.jar -Dspring.profiles.active=dev
```

