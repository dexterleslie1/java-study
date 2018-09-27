package com.future.study.http.library.testing.api;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * @author Dexterleslie.Chan
 */
@RestController
@RequestMapping(value="/http/library/api")
public class ApiController {
    private final static Logger logger= Logger.getLogger(ApiController.class);

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="postAndReturnString")
    public ResponseEntity<Map<String,Object>> postAndReturnString(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "name",defaultValue = "") String name){
        if(StringUtils.isEmpty(name)){
            throw new IllegalArgumentException("请指定名字");
        }
        Map<String,Object> mapReturn=new HashMap<>();
        mapReturn.put("dataObject","你好，"+name);
        ResponseEntity responseEntity=ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapReturn);
        return responseEntity;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="postAndReturnJSONObject")
    public ResponseEntity<Map<String,Object>> postAndReturnJSONObject(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "name",defaultValue = "") String name){
        if(StringUtils.isEmpty(name)){
            throw new IllegalArgumentException("请指定名字");
        }
        Map<String,Object> jsonObjectAsDataObject=new HashMap<>();
        jsonObjectAsDataObject.put("greeting","你好，"+name);
        Map<String,Object> mapReturn=new HashMap<>();
        mapReturn.put("dataObject",jsonObjectAsDataObject);
        ResponseEntity responseEntity=ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapReturn);
        return responseEntity;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="postAndReturnWithException")
    public ResponseEntity<Map<String,Object>> postAndReturnWithException(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "name",defaultValue = "") String name) throws Exception {
        boolean b=true;
        if(b){
            throw new Exception("测试预期异常是否出现");
        }
        return null;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value="postAndReturnJSONArray")
    public ResponseEntity<Map<String,Object>> postAndReturnJSONArray(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "name",defaultValue = "") String name){
        if(StringUtils.isEmpty(name)){
            throw new IllegalArgumentException("请指定名字");
        }
        List<String> listAsDataObject=new ArrayList<>();
        for(int i=0;i<10;i++){
            listAsDataObject.add("你好，"+name+"#"+i);
        }
        Map<String,Object> mapReturn=new HashMap<>();
        mapReturn.put("dataObject",listAsDataObject);
        ResponseEntity responseEntity=ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapReturn);
        return responseEntity;
    }

    private final static String TemporaryDirectory=System.getProperty("java.io.tmpdir");
    /**
     * 上传
     * @param name
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("upload")
    public ResponseEntity<Map<String,Object>> upload(
            @RequestParam(value = "name",defaultValue = "") String name,
            @RequestParam("file1") MultipartFile file) throws IOException {
        if(StringUtils.isEmpty(name)){
            throw new IllegalArgumentException("请指定名字");
        }
        String filename=UUID.randomUUID().toString()+".tmp";
        String fileAbsolute=TemporaryDirectory+File.separator+filename;
        File tempFile = new File(fileAbsolute);
        file.transferTo(tempFile);

        Map<String,Object> mapReturn=new HashMap<>();
        mapReturn.put("name","你好，"+name);
        mapReturn.put("file",filename);

        ResponseEntity responseEntity=ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(mapReturn);
        return responseEntity;
    }

    /**
     * 下载
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(value="download")
    public ResponseEntity<Map<String,Object>> download(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value="filename",defaultValue = "")String filename){
        if(StringUtils.isEmpty(filename)){
            throw new IllegalArgumentException("请指定文件");
        }

        FileInputStream fileInputStream=null;
        ResponseEntity responseEntity=null;
        try{
            filename=TemporaryDirectory+File.separator+ filename;
            fileInputStream=new FileInputStream(filename);
            responseEntity=ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"1.jpg\"")
                    .body(new InputStreamResource(fileInputStream));
        }catch(FileNotFoundException ex){
            logger.error(ex.getMessage(),ex);
            responseEntity=ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ex.getMessage());
            return responseEntity;
        }finally{
        }
        return responseEntity;
    }
}
