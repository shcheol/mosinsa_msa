package com.mosinsa.gateway.jwt;

import com.mosinsa.gateway.filter.AuthorizationError;
import com.mosinsa.gateway.filter.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Clock;
import java.util.Map;

import static com.mosinsa.gateway.jwt.TokenUtilKey.ACCESS_TOKEN;
import static com.mosinsa.gateway.jwt.TokenUtilKey.REFRESH_TOKEN;

@Component
@RequiredArgsConstructor
public class TokenValidator {

	private final Map<String, Token> tokenUtilMap;

	private final Clock clock;

	public TokenVo validateAndGet(TokenVo tokens) {
		Token accessTokenUtil = tokenUtilMap.get(ACCESS_TOKEN.key());
		if (accessTokenUtil.isValid(tokens.accessToken())) {
			return tokens;
		}

		String refreshToken = tokens.refreshToken();
		if (!StringUtils.hasText(refreshToken)){
			throw new AuthorizationException(AuthorizationError.EMPTY_AUTHORIZATION_TOKEN);
		}

		Token refreshTokenUtil = tokenUtilMap.get(REFRESH_TOKEN.key());
		if (refreshTokenUtil.isValid(refreshToken)) {
			String newAccessToken = accessTokenUtil.create(refreshTokenUtil.getClaims(refreshToken).getSubject(), clock);
			return new TokenVo(newAccessToken, refreshToken);
		}
		throw new AuthorizationException(AuthorizationError.JWT_VALID_ERROR);
	}

}
