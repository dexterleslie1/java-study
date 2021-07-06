package com.future.demo.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.assertj.core.api.ByteArrayAssert;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.UrlResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={Application.class})
@Slf4j
public class ApplicationTests {
    @Autowired
    MinioClient minioClient;

    @Test
    public void test() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, InternalException, XmlParserException, ErrorResponseException, InvalidBucketNameException, RegionConflictException, InvalidExpiresRangeException {
        // 判断bucket是否存在
        String bucketName = "test-bucket";
        boolean exists = this.minioClient.bucketExists(bucketName);
        Assert.assertFalse(exists);

        // 创建bucket
        this.minioClient.makeBucket(bucketName);
        exists = this.minioClient.bucketExists(bucketName);
        Assert.assertTrue(exists);

        // put object到bucket
        String objectName = "1.txt";
        ClassPathResource resource = new ClassPathResource("1.txt");
        this.minioClient.putObject(bucketName, objectName, resource.getFile().getAbsolutePath(), null);
        Iterable<Result<Item>> iterable = this.minioClient.listObjects(bucketName);
        Assert.assertEquals(1, StreamSupport.stream(iterable.spliterator(), false).count());

        // 获取bucket中object内容
        InputStream inputStream = this.minioClient.getObject(bucketName, objectName);
        String content = IOUtils.toString(inputStream, "utf-8");
        inputStream.close();
        Assert.assertEquals("这是一个测试文件", content);

        // 获取bucket中object presigned
        String presignedUrl = this.minioClient.presignedGetObject(bucketName, objectName);
        UrlResource urlResource = new UrlResource(presignedUrl);
        inputStream = urlResource.getInputStream();
        content = IOUtils.toString(inputStream, "utf-8");
        inputStream.close();
        Assert.assertEquals("这是一个测试文件", content);

        // 查询并删除bucket中object
        iterable.forEach(itemResult -> {
            try {
                this.minioClient.removeObject(bucketName, itemResult.get().objectName());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });

        // 查询所有bucket
        String randomBucketName = UUID.randomUUID().toString();
        this.minioClient.makeBucket(randomBucketName);
        List<Bucket> buckets = this.minioClient.listBuckets();
        Assert.assertEquals(2, buckets.size());

        // 删除bucket
        for(Bucket bucket : buckets) {
            String temporaryBucketName = bucket.name();
            this.minioClient.removeBucket(temporaryBucketName);
        }
        exists = this.minioClient.bucketExists(bucketName);
        Assert.assertFalse(exists);
    }
}
