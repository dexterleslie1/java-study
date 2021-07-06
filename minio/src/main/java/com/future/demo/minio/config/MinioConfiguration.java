package com.future.demo.minio.config;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {
    @Value("${minio.endpoint}")
    private String minioEndpoint;
    @Value("${minio.port:9000}")
    private int minioPort;
    @Value("${minio.root-user}")
    private String minioRootUser;
    @Value("${minio.root-password}")
    private String minioRootPassword;

    @Bean
    MinioClient minioClient() throws InvalidPortException, InvalidEndpointException {
        MinioClient minioClient = new MinioClient(minioEndpoint, minioPort, minioRootUser, minioRootPassword, false);
        return minioClient;
    }
}
