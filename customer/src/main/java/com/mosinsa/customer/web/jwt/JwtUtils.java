package com.mosinsa.customer.web.jwt;


import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

	@Value("${token.secret}")
	private String secret;

	@Value("${token.expiration}")
	private long tokenExpirationDuration;

	public boolean isValid(String token){
		try {
			JwtParser jwtParser = Jwts.parser().setSigningKey("mySecretKey".getBytes(StandardCharsets.UTF_8));
			Jws<Claims> jws = jwtParser.parseClaimsJws(token);
			return jws.getBody().getExpiration().after(new Date());
		}catch (Exception e){
			return false;
		}
	}

	public String getToken(String header){
		return header.substring(header.indexOf(" ")+1);
	}

	public String getCustomerId(String token){
		try {
			JwtParser jwtParser = Jwts.parser().setSigningKey("mySecretKey".getBytes(StandardCharsets.UTF_8));
			Jws<Claims> jws = jwtParser.parseClaimsJws(token);
			return jws.getBody().getSubject();
		}catch (Exception e){
			throw e;
		}
	}

	public String createToken(String customerId) {
		Claims claims = Jwts.claims().setSubject(customerId);

		Date now = new Date();
		Date expiration = new Date(now.getTime() + tokenExpirationDuration);
		return Jwts.builder()
				.setHeaderParam("typ","JWT")
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, secret.getBytes(StandardCharsets.UTF_8)).compact();
	}

}
