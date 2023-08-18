package com.mosinsa.customer.web.jwt;


import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {
	private static final String secret="mySecretKey";
	private static final long tokenExpirationDuration = 60 * 60 *1000L;

	public static boolean isValid(String token){
		try {
			JwtParser jwtParser = Jwts.parser().setSigningKey("mySecretKey".getBytes(StandardCharsets.UTF_8));
			Jws<Claims> jws = jwtParser.parseClaimsJws(token);
			return jws.getBody().getExpiration().after(new Date());
		}catch (Exception e){
			return false;
		}
	}

	public static String getToken(String header){
		return header.substring(header.indexOf(" ")+1);
	}

	public static String getCustomerId(String token){
		try {
			JwtParser jwtParser = Jwts.parser().setSigningKey("mySecretKey".getBytes(StandardCharsets.UTF_8));
			Jws<Claims> jws = jwtParser.parseClaimsJws(token);
			return jws.getBody().getSubject();
		}catch (Exception e){
			throw e;
		}
	}

	public static String createToken(String customerId) {
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
