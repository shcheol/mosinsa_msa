package com.mosinsa.customer.web.jwt;


import com.mosinsa.customer.db.repository.TokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${token.access.secret}")
    private String accessSecret;
    @Value("${token.access.expiration}")
    private long accessTokenExpiration;
    @Value("${token.refresh.secret}")
    private String refreshSecret;
    @Value("${token.refresh.expiration}")
    private long refreshTokenExpiration;
    private final TokenRepository repository;

    public String createAccessToken(String customerId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenExpiration);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(customerId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, accessSecret.getBytes(StandardCharsets.UTF_8)).compact();
    }

    @Transactional
    public String createRefreshToken(String customerId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenExpiration);
        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(customerId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, refreshSecret.getBytes(StandardCharsets.UTF_8)).compact();
        repository.put(customerId, refreshToken, expiration.getTime());
        return refreshToken;
    }
    public boolean isAccessTokenValid(String accessToken) {
        try {
            return getClaims(accessToken, accessSecret).getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshTokenValid(String refreshToken) {
        try {
            Claims claims = getClaims(refreshToken, refreshSecret);
            validStoredToken(repository.get(claims.getSubject()), refreshToken);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getSubjectFromAccessToken(String token) {
        return getClaims(token, accessSecret).getSubject();
    }

    public String getSubjectFromRefreshToken(String token) {
        return getClaims(token, refreshSecret).getSubject();
    }
    private void validStoredToken(String storedToken, String refreshToken){
        if (!StringUtils.hasText(storedToken)) {
            throw new IllegalArgumentException("token is empty");
        }

        if (!refreshToken.equals(storedToken)) {
            throw new IllegalArgumentException("token match fail");
        }
    }


    private Claims getClaims(String token, String secret) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token).getBody();
            if (!StringUtils.hasText(claims.getSubject())) throw new IllegalArgumentException("subject is empty");
            return claims;
        } catch (Exception e) {
            log.debug("jwt parse fail", e);
            throw new IllegalArgumentException(e);
        }
    }


}
