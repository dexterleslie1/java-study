package com.future.demo.java.encryption;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Assert;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

public class RSATests {
    @Test
    public void test() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String content = UUID.randomUUID().toString();

        String algorithm = "RSA";
        String plainSecretKey = UUID.randomUUID().toString();
        int keySize = 512;

        // 生成秘钥对
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize, new SecureRandom(plainSecretKey.getBytes()));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        byte []privateKeyBytes = keyPair.getPrivate().getEncoded();
        byte []publicKeyBytes = keyPair.getPublic().getEncoded();

        // 私钥加密，公钥解密
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte []encryptBytes = cipher.doFinal(content.getBytes());

        keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte []decryptBytes = cipher.doFinal(encryptBytes);
        String contentProcessed = new String(decryptBytes);
        Assert.assertEquals(content, contentProcessed);

        // 公钥加密，私钥解密
        keyFactory = KeyFactory.getInstance(algorithm);
        publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        encryptBytes = cipher.doFinal(content.getBytes());

        keyFactory = KeyFactory.getInstance(algorithm);
        privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        decryptBytes = cipher.doFinal(encryptBytes);
        contentProcessed = new String(decryptBytes);
        Assert.assertEquals(content, contentProcessed);
    }

    @Test
    public void testOpenSSLKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String publicKeyString = System.getenv("publicKey");
        String privateKeyString = System.getenv("privateKey");

        String content = UUID.randomUUID().toString();

        String algorithm = "RSA";

        byte []privateKeyBytes = Base64.decode(privateKeyString);
        byte []publicKeyBytes = Base64.decode(publicKeyString);

        // 私钥加密，公钥解密
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte []encryptBytes = cipher.doFinal(content.getBytes());

        keyFactory = KeyFactory.getInstance(algorithm);
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte []decryptBytes = cipher.doFinal(encryptBytes);
        String contentProcessed = new String(decryptBytes);
        Assert.assertEquals(content, contentProcessed);

        // 公钥加密，私钥解密
        keyFactory = KeyFactory.getInstance(algorithm);
        publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        encryptBytes = cipher.doFinal(content.getBytes());

        keyFactory = KeyFactory.getInstance(algorithm);
        privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        decryptBytes = cipher.doFinal(encryptBytes);
        contentProcessed = new String(decryptBytes);
        Assert.assertEquals(content, contentProcessed);
    }

    @Test
    public void digitalSignAndVerify() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        String publicKeyString = System.getenv("publicKey");
        String privateKeyString = System.getenv("privateKey");

        String content = UUID.randomUUID().toString();

        byte []privateKeyBytes = Base64.decode(privateKeyString);
        byte []publicKeyBytes = Base64.decode(publicKeyString);

        // 签名
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyf = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyf.generatePrivate(priPKCS8);
        java.security.Signature signature = java.security.Signature.getInstance("SHA256WithRSA");
        signature.initSign(priKey);
        signature.update(content.getBytes());
        byte[] signed = signature.sign();
        String contentSigned = Base64.encode(signed);

        // 签名验证
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        signature = java.security.Signature.getInstance("SHA256WithRSA");
        signature.initVerify(pubKey);
        signature.update(content.getBytes());
        boolean bverify = signature.verify(Base64.decode(contentSigned));
        Assert.assertTrue(bverify);
    }
}
