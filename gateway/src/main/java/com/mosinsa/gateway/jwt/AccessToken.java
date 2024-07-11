package com.mosinsa.gateway.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AccessToken implements Token {

    @Value("${token.access.secret}")
    private String secret;

    @Value("${token.access.expiration}")
    private long tokenExpiration;

	private final Clock clock;

    @Override
    public String create(String customerId) {
		return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(customerId)
                .setIssuedAt(new Date(clock.millis()))
                .setExpiration(new Date(clock.millis() + tokenExpiration))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8)).compact();
    }

	public String create(String customerId, Clock clock) {
		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject(customerId)
				.setIssuedAt(new Date(clock.millis()))
				.setExpiration(new Date(clock.millis() + tokenExpiration))
				.signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8)).compact();
	}

    @Override
    public boolean isValid(String token) {
		try {
			getClaims(token, secret);
			return true;
		} catch (Exception e){
			System.out.println(e);
			return false;
		}
    }

    @Override
    public String getSubject(String token) {
        return getClaims(token, secret).getSubject();
    }
}
