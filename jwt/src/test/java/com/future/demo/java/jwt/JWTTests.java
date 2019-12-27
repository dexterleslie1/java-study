package com.future.demo.java.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class JWTTests {
    private final static String Secret = "123456";
    /**
     *
     */
    @Test
    public void createSignToken() {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("type", "JWT");
        headerMap.put("alg", "HS256");
        String token = JWT.create()
                // header
                .withHeader(headerMap)
                // payload
                .withClaim("userId", "12345")
                .withClaim("loginname", "ak123456")
                .sign(Algorithm.HMAC256(Secret));
        Assert.assertNotNull(token);
    }

    @Test
    public void verifyToken() {
        Long userId = 12345678l;
        String loginname = "ak123456";

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("type", "JWT");
        headerMap.put("alg", "HS256");
        String token = JWT.create()
                // header
                .withHeader(headerMap)
                // payload
                .withClaim("userId", userId)
                .withClaim("loginname", loginname)
                .sign(Algorithm.HMAC256(Secret));

        Algorithm algorithm = Algorithm.HMAC256(Secret);
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
