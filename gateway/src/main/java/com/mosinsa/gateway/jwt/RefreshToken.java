package com.mosinsa.gateway.jwt;

import com.mosinsa.gateway.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshToken implements Token {

	@Value("${token.refresh.secret}")
	private String secret;
	@Value("${token.refresh.expiration}")
	private long tokenExpiration;
	private final TokenRepository repository;

	@Override
	public String create(String customerId, Clock clock) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + tokenExpiration);
		String refreshToken = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setSubject(customerId)
				.setIssuedAt(now)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8)).compact();

		repository.put(customerId, refreshToken, tokenExpiration);

		return refreshToken;
	}

	@Override
	public Claims getClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
				.parseClaimsJws(token).getBody();
	}

	@Override
	public boolean isValid(String token) {
		try {
			Claims claims = getClaims(token);
			validStoredToken(repository.get(claims.getSubject()), token);
			return true;
		} catch (Exception e) {
			log.error("invalid token",e);
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

	public void remove(String token){
		String subject = getClaims(token).getSubject();
		repository.remove(subject);
	}
}
