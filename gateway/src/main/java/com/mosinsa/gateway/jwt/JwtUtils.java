package com.mosinsa.gateway.jwt;


import com.mosinsa.gateway.repository.TokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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
	public boolean isAccessTokenValid(String accessToken){
		try {
			Claims claims = getClaims(accessToken, accessSecret);

			if(!StringUtils.hasText(claims.getSubject())){
				return false;
			}

			return claims.getExpiration().after(new Date());

		}catch (Exception e){
			return false;
		}
	}

	public boolean isRefreshTokenValid(String refreshToken){
		try {

			Claims claims = getClaims(refreshToken, refreshSecret);

			String customerId = getSubject(refreshToken);
			if(!StringUtils.hasText(customerId)){
				return false;
			}

			String storedToken = repository.get(customerId);
			if(!StringUtils.hasText(storedToken)){
				return false;
			}

			if (!refreshToken.equals(storedToken)){
				return false;
			}

			return claims.getExpiration().after(new Date());

		}catch (Exception e){
			return false;
		}
	}

	public String getSubject(String token){
		try {
			return Jwts.parser().setSigningKey(refreshSecret.getBytes(StandardCharsets.UTF_8))
					.parseClaimsJws(token).getBody().getSubject();

		}catch (Exception e){
			log.debug("jwt parse fail", e);
			throw new IllegalArgumentException(e);
		}
	}

	private Claims getClaims(String token, String secret){
		try{
			return Jwts.parser().setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
					.parseClaimsJws(token).getBody();
		}catch (Exception e){
			log.debug("jwt parse fail", e);
			throw new IllegalArgumentException(e);
		}
	}

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

}
