package com.future.demo.java.encryption;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

/**
 *
 */
public class JWTTests {
    private String secret;

    @Before
    public void init() {
        this.secret = System.getenv("secret");
    }

    @Test
    public void signWithHMAC256() {
        String token = JWT.create()
                // payload
                .withClaim("userId", "12345")
                .withClaim("loginname", "ak123456")
                .sign(Algorithm.HMAC256(secret));
        Assert.assertNotNull(token);

        String []tokenArray = token.split("\\.");
        String tokenHeader = tokenArray[0];
        String tokenHeaderDecode = new String(Base64.getUrlDecoder().decode(tokenHeader), StandardCharsets.UTF_8);
        String tokenPayload = tokenArray[1];
        String tokenPayloadDecode = new String(Base64.getUrlDecoder().decode(tokenPayload), StandardCharsets.UTF_8);
        String tokenSignature = tokenArray[2];

        System.out.println("token=" + token);
        System.out.println("tokenHeader=" + tokenHeader);
        System.out.println("tokenHeaderDecode=" + tokenHeaderDecode);
        System.out.println("tokenPayload=" + tokenPayload);
        System.out.println("tokenPayloadDecode=" + tokenPayloadDecode);
        System.out.println("tokenSignature=" + tokenSignature);
    }

    @Test
    public void signWithRSAAndVerity() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException, SignatureException {
        String publicKeyString = System.getenv("publicKey");
        String privateKeyString = System.getenv("privateKey");
        byte []privateKeyBytes = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode(privateKeyString);
        byte []publicKeyBytes = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode(publicKeyString);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privateKey = (RSAPrivateKey)keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        RSAPublicKey publicKey = (RSAPublicKey)keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        Algorithm algorithm = Algorithm.RSA512(publicKey, privateKey);

        String token = JWT.create()
                // payload
                .withClaim("userId", "12345")
                .withClaim("loginname", "ak123456")
                .sign(algorithm);
        Assert.assertNotNull(token);

        String []tokenArray = token.split("\\.");
        String tokenHeader = tokenArray[0];
        String tokenHeaderDecode = new String(Base64.getUrlDecoder().decode(tokenHeader), StandardCharsets.UTF_8);
        String tokenPayload = tokenArray[1];
        String tokenPayloadDecode = new String(Base64.getUrlDecoder().decode(tokenPayload), StandardCharsets.UTF_8);
        String tokenSignature = tokenArray[2];

        System.out.println("token=" + token);
        System.out.println("tokenHeader=" + tokenHeader);
        System.out.println("tokenHeaderDecode=" + tokenHeaderDecode);
        System.out.println("tokenPayload=" + tokenPayload);
        System.out.println("tokenPayloadDecode=" + tokenPayloadDecode);
        System.out.println("tokenSignature=" + tokenSignature);

        boolean verifyResult;
        try {
            algorithm = Algorithm.RSA512(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    //more validations if needed
                    .build();
            verifier.verify(token);
            verifyResult = true;
        } catch (Exception e){
            e.printStackTrace();
            verifyResult = false;
        }
        Assert.assertTrue(verifyResult);
    }

    @Test
    public void verifyToken() {
        Long userId = 12345678l;
        String loginname = "ak123456";

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("alg", "HS256");
        String token = JWT.create()
                // header
                .withHeader(headerMap)
                // payload
                .withClaim("userId", userId)
                .withClaim("loginname", loginname)
                .sign(Algorithm.HMAC256(secret));

        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claimMap = decodedJWT.getClaims();

        Long claimUserId = claimMap.get("userId").asLong();
        String claimLoginname = claimMap.get("loginname").asString();
        Assert.assertEquals(userId, claimUserId);
        Assert.assertEquals(loginname, claimLoginname);

        Date issueAt  = decodedJWT.getIssuedAt();
        Date expireAt = decodedJWT.getExpiresAt();
        Assert.assertNull(issueAt);
        Assert.assertNull(expireAt);
    }
}
