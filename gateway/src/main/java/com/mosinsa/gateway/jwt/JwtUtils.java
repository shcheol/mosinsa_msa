package com.mosinsa.gateway.jwt;


import com.mosinsa.gateway.repository.TokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
	public boolean isAccessTokenValid(String accessToken){
		try {
			Claims claims = Jwts.parser().setSigningKey(accessSecret.getBytes(StandardCharsets.UTF_8))
					.parseClaimsJws(accessToken).getBody();

			if(!StringUtils.hasText(claims.getSubject())){
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
			throw new IllegalArgumentException(e);
		}
	}

	public boolean isRefreshTokenValid(String refreshToken){
		try {
			// io.jsonwebtoken.ExpiredJwtException: JWT expired at 2023-08-22T17:24:51Z. Current time: 2023-08-22T17:26:46Z, a difference of 115085 milliseconds.  Allowed clock skew: 0 milliseconds.
//			Claims claims = Jwts.parser().setSigningKey(accessSecret.getBytes(StandardCharsets.UTF_8))
//					.parseClaimsJws(accessToken).getBody();
//
//			if(!StringUtils.hasText(claims.getSubject())){
//				return false;
//			}

			Claims refreshClaims = Jwts.parser().setSigningKey(refreshSecret.getBytes(StandardCharsets.UTF_8))
					.parseClaimsJws(refreshToken).getBody();

			if(!StringUtils.hasText(refreshClaims.getSubject())){
				return false;
			}

//			if (claims.getSubject() != refreshClaims.getSubject()){
//				return false;
//			}

			return refreshClaims.getExpiration().after(new Date());

		}catch (Exception e){
			return false;
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
