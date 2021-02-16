package com.future.demo.network.optimization.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 *
 */
@SpringBootApplication
public class Application {
    /**
     *
     * @param args
     */
    public static void main(String []args){
        SpringApplication.run(Application.class, args);

        // 删除预先准备的下载文件
        String temporaryDirectory = System.getProperty("java.io.tmpdir");
        String filename = temporaryDirectory + "spring-boot-upload-download.tmp";
        File file = new File(filename);
        file.delete();
    }
}
