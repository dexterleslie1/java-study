package com.future.demo.network.optimization.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 */
@RestController
@RequestMapping(value="/api/v1")
public class ApiController {
    @Autowired
    private SessionRegistry sessionRegistry;

    /**
     * 服务器推送websocket消息
     * @param message
     * @return
     * @throws IOException
     */
    @PostMapping(value = "pushMessage")
    ResponseEntity<String> pushMessage(
            @RequestParam(value = "message") String message) throws IOException {
        List<WebSocketSession> sessionList = this.sessionRegistry.getSessionList();
        for(WebSocketSession session : sessionList) {
            session.sendMessage(new TextMessage("服务器推送消息：" + message));
        }
        return ResponseEntity.ok("消息：\"" + message + "\" 推送成功");
    }

    /**
     * 模拟post body请求
     * @param parameters
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value="postBody")
    ResponseEntity<String> postBody(
            @RequestBody Map<String, Object> parameters) throws JsonProcessingException {
        ObjectMapper OMInstance = new ObjectMapper();
        String json = OMInstance.writeValueAsString(parameters);
        return ResponseEntity.ok("你提交的json：" + json);
    }

    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping(value = "uploadFile")
    ResponseEntity<String> uploadFile(
            @RequestParam("file1") MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("upload", ".tmp");
        file.transferTo(tempFile);
        return ResponseEntity.ok("上传文件保存临时路径：" + tempFile.getAbsolutePath());
    }

    /**
     * 下载文件
     * @return
     */
    @PostMapping(value = "downloadFile")
    ResponseEntity downloadFile(){
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
            List<String> chars = new ArrayList<>();
            int startChar = 'a';
            for(int i=startChar; i<=startChar+26; i++) {
                chars.add(String.valueOf((char)i));
            }
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                Random random = new Random();
                StringBuilder builder = new StringBuilder();
                int size = chars.size();
                for(int j=0; j<1024*1024; j++) {
                    int randomInt = random.nextInt(size);
                    builder.append(chars.get(randomInt));
                }
                outputStream.write(builder.toString().getBytes("utf-8"));
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
