演示spring-cloud配置中心用法

项目config-center-repository为demo配置中心配置文件仓库

参考
https://www.cnblogs.com/fengzheng/p/11242128.html

调试config server是否正常
获取main分支application.properties文件
GET http://localhost:8888/application/default/main

获取main分支application-dev.properties文件
GET http://localhost:8888/application/dev/main