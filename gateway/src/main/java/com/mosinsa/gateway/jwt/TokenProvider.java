package com.mosinsa.gateway.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenProvider {

	private final Map<String, Token> tokenUtilMap;
	private final Clock clock;

	public TokenVo create(String customerId) {
		return new TokenVo(
				tokenUtilMap.get(TokenUtilKey.ACCESS_TOKEN.key()).create(customerId, clock),
				tokenUtilMap.get(TokenUtilKey.REFRESH_TOKEN.key()).create(customerId, clock)
		);
	}

	public void remove(String token){
		RefreshToken refreshTokenUtil = (RefreshToken) tokenUtilMap.get(TokenUtilKey.REFRESH_TOKEN.key());
		refreshTokenUtil.remove(token);
	}

}
