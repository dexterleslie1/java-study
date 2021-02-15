package com.future.demo.spring.boot.upload.download;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Dexterleslie.Chan
 */
@RestController
@RequestMapping(value="/api/v1")
public class ApiController {
    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "upload")
    public ResponseEntity<String> upload(
            @RequestParam("file1") MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("upload", ".tmp");
        file.transferTo(tempFile);
        System.out.println("上传文件：" + file.getOriginalFilename() + " 被临时保存路径：" + tempFile.getAbsolutePath());
        return ResponseEntity.ok("上传文件保存临时路径：" + tempFile.getAbsolutePath());
    }

    /**
     * 下载文件
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "download")
    public ResponseEntity download(
            HttpServletRequest request,
            HttpServletResponse response){
        FileOutputStream fileOutputStream=null;
        FileInputStream fileInputStream=null;
        ResponseEntity responseEntity=null;
        try{
            File file = createFileIfNotExists();

            fileInputStream=new FileInputStream(file);
            responseEntity=ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(new InputStreamResource(fileInputStream));
        } catch(Exception ex) {
            ex.printStackTrace();
            responseEntity=ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(ex.getMessage());
            return responseEntity;
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    fileOutputStream = null;
                }
            }catch(IOException ex){
            }
        }
        return responseEntity;
    }

    final static String TemporaryDirectory=System.getProperty("java.io.tmpdir");
    File createFileIfNotExists() throws IOException {
        String filename = TemporaryDirectory + "spring-boot-upload-download.tmp";
        File file = new File(filename);
        if(!file.exists()) {
            file.createNewFile();
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                outputStream.write("测试文件".getBytes("utf-8"));
            } catch (Exception ex) {
                throw ex;
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }
            }
        }
        return file;
    }
}
