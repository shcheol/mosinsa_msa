package com.mosinsa.customer.web.jwt;


import com.mosinsa.customer.db.repository.TokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Date;

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
				.setHeaderParam("typ","JWT")
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
}
