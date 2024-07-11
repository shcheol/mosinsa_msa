package com.mosinsa.gateway.jwt;

import com.mosinsa.gateway.filter.AuthorizationError;
import com.mosinsa.gateway.filter.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

import static com.mosinsa.gateway.jwt.TokenMapEnums.*;

@Component
@RequiredArgsConstructor
public class TokenValidator {

	private final Map<String, Token> tokenUtilMap;

	public TokenDto validateAndGet(TokenDto tokens) {
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
			String newAccessToken = accessTokenUtil.create(refreshTokenUtil.getSubject(refreshToken));
			return new TokenDto(newAccessToken, refreshToken);
		}
		throw new AuthorizationException(AuthorizationError.JWT_VALID_ERROR);
	}

}
