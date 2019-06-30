package com.future.study.jedis.cluster;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Dexterleslie.Chan
 */
public class PropertyUtil {
    //加载property文件到io流里面
    public static Properties loadProperties(String propertyFile) {
        Properties properties = new Properties();
        try {
            InputStream is = PropertyUtil.class.getClassLoader().getResourceAsStream(propertyFile);
            if(is == null){
                is = PropertyUtil.class.getClassLoader().getResourceAsStream(propertyFile);
            }
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 根据key值取得对应的value值
     *
     * @param key
     * @return
     */
    public static String getValue(String propertyFile, String key) {
        Properties properties = loadProperties(propertyFile);
        return properties.getProperty(key);
    }
}
