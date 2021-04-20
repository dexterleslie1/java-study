package com.future.demo.java.encryption;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public class DESTests {
    /**
     *
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    @Test
    public void testDESEncryptDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        StringBuilder builder = new StringBuilder();
        for(int i=0 ; i<1000; i++) {
            builder.append(UUID.randomUUID().toString());
        }
        String content = builder.toString();

        String algorithm = "DES";
        String plainSecretKey = UUID.randomUUID().toString();
        // 生成秘钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(new SecureRandom(plainSecretKey.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] secretKeyBytes = secretKey.getEncoded();

        // 加密
        secretKey = new SecretKeySpec(secretKeyBytes, algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte []encryptBytes = cipher.doFinal(content.getBytes());

        // 解密
        secretKey = new SecretKeySpec(secretKeyBytes, algorithm);
        cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte []plainBytes = cipher.doFinal(encryptBytes);

        String contentProcessed = new String(plainBytes);
        Assert.assertEquals(content, contentProcessed);
    }

    /**
     *
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    @Test
    public void testDES3EncryptDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        StringBuilder builder = new StringBuilder();
        for(int i=0 ; i<1000; i++) {
            builder.append(UUID.randomUUID().toString());
        }
        String content = builder.toString();

        String algorithm = "DESede";
        String plainSecretKey = UUID.randomUUID().toString();
        // 生成秘钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(new SecureRandom(plainSecretKey.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] secretKeyBytes = secretKey.getEncoded();

        // 加密
        secretKey = new SecretKeySpec(secretKeyBytes, algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte []encryptBytes = cipher.doFinal(content.getBytes());

        // 解密
        secretKey = new SecretKeySpec(secretKeyBytes, algorithm);
        cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte []plainBytes = cipher.doFinal(encryptBytes);

        String contentProcessed = new String(plainBytes);
        Assert.assertEquals(content, contentProcessed);
    }

    /**
     *
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    @Test
    public void testAESEncryptDecrypt() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        StringBuilder builder = new StringBuilder();
        for(int i=0 ; i<1000; i++) {
            builder.append(UUID.randomUUID().toString());
        }
        String content = builder.toString();

        String algorithm = "AES";
        String plainSecretKey = UUID.randomUUID().toString();
        // 生成秘钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(new SecureRandom(plainSecretKey.getBytes()));
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] secretKeyBytes = secretKey.getEncoded();

        // 加密
        secretKey = new SecretKeySpec(secretKeyBytes, algorithm);
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte []encryptBytes = cipher.doFinal(content.getBytes());

        // 解密
        secretKey = new SecretKeySpec(secretKeyBytes, algorithm);
        cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte []plainBytes = cipher.doFinal(encryptBytes);

        String contentProcessed = new String(plainBytes);
        Assert.assertEquals(content, contentProcessed);
    }
}
