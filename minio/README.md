##### docker运行minio
http://docs.minio.org.cn/docs/master/minio-docker-quickstart-guide
```shell script
docker run --rm --name minio-demo -p 9000:9000 minio/minio:RELEASE.2021-06-17T00-10-46Z server /data
```

##### Java Client API参考文档
https://www.jianshu.com/p/d10f8fdbf7cd
http://docs.minio.org.cn/docs/master/java-client-api-reference