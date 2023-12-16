package com.mosinsa.gateway.jwt;

import com.mosinsa.gateway.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AccessToken implements Token {

    @Value("${token.access.secret}")
    private String secret;

    @Value("${token.access.expiration}")
    private long tokenExpiration;

    private final TokenRepository repository;

    @Override
    public String create(String customerId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + tokenExpiration);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(customerId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8)).compact();

    }

    @Override
    public boolean isValid(String token) {
        Claims claims = getClaims(token, secret);
        Date now = new Date();
        return claims.getIssuedAt().before(now)
                && claims.getExpiration().after(now);
    }

    @Override
    public void remove(String token) {
        repository.remove(getSubject(token));
    }

    @Override
    public String getSubject(String token) {
        return getClaims(token, secret).getSubject();
    }
}
