package com.future.demo.java.aliyun.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * 下载小文件测试
 */
public class DownloadTests {
    /**
     *
     */
    @Test
    public void download() throws IOException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = System.getenv("accessKeyID");
        String accessKeySecret = System.getenv("accessKeySecret");
        String bucketName = System.getenv("bucketName");
        String objectName = "certificates/chat-apns-iOS-dev.p12";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        File file = File.createTempFile("cert", ".tmp");
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), file);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
