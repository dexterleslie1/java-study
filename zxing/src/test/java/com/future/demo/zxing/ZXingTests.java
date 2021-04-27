package com.future.demo.zxing;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.Hashtable;


public class ZXingTests {
    @Test
    public void test() throws Exception {
        // 生成二维码
        String text="www.baidu.com";
        int width=100;
        int height=100;
        String format="png";
        Hashtable hints=new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.MARGIN, 2);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);
        Path file = File.createTempFile("zxing", ".tmp").toPath();
        MatrixToImageWriter.writeToPath(bitMatrix, format, file);

        String contentFromFile = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file.toFile());
            contentFromFile = IOUtils.toString(inputStream, "utf-8");
        } catch (Exception ex) {
            throw ex;
        } finally {
            if(inputStream!=null) {
                inputStream.close();
                inputStream = null;
            }
        }

        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);

            String contentFromByteArray = IOUtils.toString(outputStream.toByteArray(), "utf-8");
            Assert.assertEquals(contentFromByteArray, contentFromFile);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if(outputStream!=null) {
                outputStream.close();
                outputStream = null;
            }
        }

        // 识别二维码图片提取url
        MultiFormatReader formatReader=new MultiFormatReader();
        BufferedImage image=null;
        try {
            image = ImageIO.read(file.toFile());
        } catch (IOException e) {
            throw e;
        }
        BinaryBitmap binaryBitmap =new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        hints=new Hashtable();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        Result result=formatReader.decode(binaryBitmap,hints);
        Assert.assertEquals(BarcodeFormat.QR_CODE, result.getBarcodeFormat());
        Assert.assertEquals(text, result.getText());
    }
}
