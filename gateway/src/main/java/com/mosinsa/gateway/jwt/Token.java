package com.mosinsa.gateway.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

public interface Token {

    String create(String customerId);

    String getSubject(String token);

    boolean isValid(String token);

    void remove(String token);

    default Claims getClaims(String token, String secret) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();

            if (!StringUtils.hasText(claims.getSubject())) {
                throw new IllegalArgumentException("subject is empty");
            }
            if (!StringUtils.hasText(claims.getIssuedAt().toString())) {
                throw new IllegalArgumentException("issued date is empty");
            }
            if (!StringUtils.hasText(claims.getExpiration().toString())) {
                throw new IllegalArgumentException("expiration date is empty");
            }
            return claims;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
