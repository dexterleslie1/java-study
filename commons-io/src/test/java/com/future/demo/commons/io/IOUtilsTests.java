package com.future.demo.commons.io;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

/**
 *
 */
public class IOUtilsTests {
    /**
     *
     */
    @Test
    public void test() throws IOException {
        File file = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String content1 = null;
        try{
            inputStream = IOUtilsTests.class.getClassLoader().getResourceAsStream("1.txt");
            content1 = IOUtils.toString(inputStream, "utf-8");
            if(inputStream!=null) {
                inputStream.close();
                inputStream = null;
            }

            file = File.createTempFile("file", ".tmp");

            inputStream = IOUtilsTests.class.getClassLoader().getResourceAsStream("1.txt");
            outputStream = new FileOutputStream(file);
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if(inputStream!=null) {
                inputStream.close();
                inputStream = null;
            }

            if(outputStream!=null) {
                outputStream.close();
                outputStream = null;
            }
        }

        try {
            inputStream = new FileInputStream(file);
            String content2 = IOUtils.toString(inputStream, "utf-8");
            Assert.assertEquals(content1, content2);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if(inputStream!=null) {
                inputStream.close();
                inputStream = null;
            }
        }
    }
}
