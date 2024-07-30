package com.mosinsa.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccessToken implements Token {

	@Value("${token.access.secret}")
	private String secret;

	@Value("${token.access.expiration}")
	private long tokenExpiration;


	@Override
	public String create(String customerId, Clock clock) {
		long millis = clock.millis();
		return Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject(customerId)
				.setIssuedAt(new Date(millis))
				.setExpiration(new Date(millis + tokenExpiration))
				.signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8)).compact();
	}

	@Override
	public boolean isValid(String token) {
		try {
			getClaims(token);
			return true;
		} catch (Exception e) {
			log.error("invalid token", e);
			return false;
		}
	}

	@Override
	public Claims getClaims(String token) {

		return Jwts.parser()
				.setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(token).getBody();

	}
}
