package org.example.auth;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;

import java.security.Key;

public class JwtUtils {
    private static Key SECRET_KEY;

    public static void initialize(String secret) {
        SECRET_KEY = new HmacKey(secret.getBytes());
    }

    public static String generateToken(String username) throws JoseException {
        JwtClaims claims = new JwtClaims();
        claims.setSubject("1");
        claims.setStringClaim("username", username);
        claims.setIssuedAtToNow();
        claims.setGeneratedJwtId();

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue("HS256");
        jws.setKey(SECRET_KEY);

        return jws.getCompactSerialization();
    }

    public static JwtClaims parseToken(String token) throws Exception {
        JwtConsumer consumer = new JwtConsumerBuilder()
                .setAllowedClockSkewInSeconds(300)
                .setRequireSubject()
                .setVerificationKey(SECRET_KEY)
                .build();

        return consumer.processToClaims(token);
    }
}
