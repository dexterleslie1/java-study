package com.future.demo.ip;

import org.junit.Assert;
import org.junit.Test;
import org.lionsoul.ip2region.*;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class Ip2regionTests {
    @Test
    public void test() throws Exception {
        UrlResource urlResource = new UrlResource("https://bucketxy.oss-cn-hangzhou.aliyuncs.com/ip/ip2region.db");
        File temporaryFile = File.createTempFile("ip2region", ".tmp");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(temporaryFile);
            StreamUtils.copy(urlResource.getInputStream(), outputStream);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if(outputStream!=null) {
                outputStream.close();
                outputStream = null;
            }
        }

        String dbPath = temporaryFile.getAbsolutePath();
        //查询算法
        int algorithm = DbSearcher.BTREE_ALGORITHM; //B-tree
        //DbSearcher.BINARY_ALGORITHM //Binary
        //DbSearcher.MEMORY_ALGORITYM //Memory
        DbConfig config = new DbConfig();
        DbSearcher searcher = new DbSearcher(config, dbPath);

        //define the method
        Method method = null;
        switch (algorithm) {
            case DbSearcher.BTREE_ALGORITHM:
                method = searcher.getClass().getMethod("btreeSearch", String.class);
                break;
            case DbSearcher.BINARY_ALGORITHM:
                method = searcher.getClass().getMethod("binarySearch", String.class);
                break;
            case DbSearcher.MEMORY_ALGORITYM:
                method = searcher.getClass().getMethod("memorySearch", String.class);
                break;
        }

        List<String> ips = Arrays.asList(new String[]{
                "120.230.113.153"/* 中国移动 */,
                "219.137.75.220"/* 中国电信 */,
                "43.226.160.23"/* 中国联通 */,
                "103.3.128.23"/* 中国铁通 */,
                "43.252.48.23"/* 中国教育网 */,
                "45.122.40.23"/* 中国鹏博士 */,
                "23.91.97.126"/* 中国香港 */,
                "103.237.124.23"/* 中国澳门 */,
                "43.224.248.23"/* 中国台湾 */,
                "37.220.32.23"/* 荷兰 */,
                "187.69.39.102"/* 巴西 */});
        for(String ip : ips) {
            DataBlock dataBlock = null;
            if (Util.isIpAddress(ip) == false) {
                System.out.println("Error: Invalid ip address");
            }

            dataBlock = (DataBlock) method.invoke(searcher, ip);

            String region = dataBlock.getRegion();
            System.out.println(ip + " - " + region);
        }
    }
}