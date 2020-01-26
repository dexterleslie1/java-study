package com.future.demo.java.spring.resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.*;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;
import java.util.UUID;

/**
 *
 */
public class ResourceTests {
    /**
     *
     */
    @Test
    public void testClassPathResource() throws IOException {
        ClassPathResource resource = new ClassPathResource("file-none-exists.properties");
        Assert.assertTrue(!resource.exists());

        resource = new ClassPathResource("file.properties");
        Assert.assertTrue(resource.exists());
        File file = resource.getFile();
        Assert.assertNotNull(file);
    }

    /**
     *
     * @throws IOException
     */
    @Test
    public void testFileSystemResource() throws IOException {
        ClassPathResource resource = new ClassPathResource("file.properties");
        String path = resource.getURL().getPath();
        FileSystemResource fileSystemResource = new FileSystemResource(path);
        Assert.assertTrue(fileSystemResource.exists());
    }

    /**
     *
     * @throws MalformedURLException
     */
    @Test
    public void testUrlResource() throws IOException {
        UrlResource urlResource = new UrlResource("https://docs.spring.io/spring/docs/4.0.0.M1/spring-framework-reference/pdf/spring-framework-reference.pdf");
        File temporaryFile = File.createTempFile("file", ".tmp");
        FileOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            outputStream = new FileOutputStream(temporaryFile);
            StreamUtils.copy(urlResource.getInputStream(), outputStream);

            inputStream = new FileInputStream(temporaryFile);
            Assert.assertEquals(urlResource.contentLength(), inputStream.available());
        } catch (Exception ex) {
            throw ex;
        } finally {
            if(outputStream!=null) {
                outputStream.close();
                outputStream = null;
            }

            if(inputStream!=null) {
                inputStream.close();
                inputStream = null;
            }
        }
    }

    /**
     *
     */
    @Test
    public void testByteArrayResource() {
        byte [] randomBytes = new byte[1024*100];
        Random random = new Random();
        random.nextBytes(randomBytes);
        ByteArrayResource byteArrayResource = new ByteArrayResource(randomBytes);
        Assert.assertEquals(randomBytes, byteArrayResource.getByteArray());
    }

    /**
     *
     */
    @Test
    public void testResourceLoader() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("file.properties");
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("file.properties");
        Assert.assertTrue(resource instanceof ClassPathResource);

        resource = resourceLoader.getResource("classpath:file.properties");
        Assert.assertTrue(resource instanceof ClassPathResource);

        String absolutePath = "file:" + classPathResource.getFile().getAbsolutePath();
        resource = resourceLoader.getResource(absolutePath);
        Assert.assertTrue(resource instanceof FileUrlResource);

        resource = resourceLoader.getResource("https://docs.spring.io/spring/docs/4.0.0.M1/spring-framework-reference/pdf/spring-framework-reference.pdf");
        Assert.assertTrue(resource instanceof UrlResource);
    }
}
