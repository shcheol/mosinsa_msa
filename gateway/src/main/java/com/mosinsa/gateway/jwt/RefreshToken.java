package com.mosinsa.gateway.jwt;

import com.mosinsa.gateway.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class RefreshToken implements Token {

    @Value("${token.refresh.secret}")
    private String secret;
    @Value("${token.refresh.expiration}")
    private long tokenExpiration;
    private final TokenRepository repository;

    @Override
    public String create(String customerId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + tokenExpiration);
        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(customerId)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8)).compact();

        repository.put(customerId, refreshToken, expiration.getTime());

        return refreshToken;
    }

    @Override
    public String getSubject(String token) {
        return getClaims(token, secret).getSubject();
    }

    @Override
    public boolean isValid(String token) {
		try {
			Claims claims = getClaims(token, secret);
			validStoredToken(repository.get(claims.getSubject()), token);
			return true;
		} catch (Exception e){
			return false;
		}
    }

    private void validStoredToken(String storedToken, String refreshToken) {
        if (!StringUtils.hasText(storedToken)) {
            throw new IllegalArgumentException("token is empty");
        }

        if (!refreshToken.equals(storedToken)) {
            throw new IllegalArgumentException("token match fail");
        }
    }
}
