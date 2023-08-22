package com.mosinsa.customer.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.customer.service.CustomerService;
import com.mosinsa.customer.web.controller.request.RequestLogin;
import com.mosinsa.customer.web.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;


@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private static final ObjectMapper om = new ObjectMapper();
	private final CustomerService customerService;
	private final JwtUtils jwtUtils;


	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) throws AuthenticationException {

		try {
			RequestLogin credentials = om.readValue(request.getInputStream(), RequestLogin.class);

			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(credentials.getLoginId(), credentials.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
											HttpServletResponse response,
											FilterChain chain,
											Authentication authResult) {
		String id = customerService.findByLoginId(((User) authResult.getPrincipal()).getUsername()).getId();

		response.addHeader("customerId", id);
		response.addHeader("accessToken", jwtUtils.createAccessToken(id));
		response.addHeader("refreshToken", jwtUtils.createRefreshToken(id));
	}
}