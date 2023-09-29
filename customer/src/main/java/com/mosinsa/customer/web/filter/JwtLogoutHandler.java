package com.mosinsa.customer.web.filter;

import com.mosinsa.customer.db.repository.TokenRepository;
import com.mosinsa.customer.web.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Slf4j
public class JwtLogoutHandler implements LogoutHandler {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private TokenRepository repository;


	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		log.info("Jwt logout");

		String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.info("access Token {}", accessToken);
		if (jwtUtils.isAccessTokenValid(accessToken)){
			log.info("Jwt isAccessTokenValid");
			String customerId = jwtUtils.getSubjectFromAccessToken(accessToken);
			repository.remove(customerId);
			return;
		}

		String refreshToken = request.getHeader(HeaderConst.REFRESH_TOKEN.getName());
		log.info("refresh Token {}", refreshToken);
		if (jwtUtils.isRefreshTokenValid(refreshToken)){
			log.info("Jwt isRefreshTokenValid");
			String customerId = jwtUtils.getSubjectFromRefreshToken(refreshToken);
			repository.remove(customerId);
		}
	}
}
