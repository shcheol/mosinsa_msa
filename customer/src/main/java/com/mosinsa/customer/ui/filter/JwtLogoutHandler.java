package com.mosinsa.customer.ui.filter;

import com.mosinsa.customer.common.jwt.Token;
import com.mosinsa.customer.common.jwt.TokenConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

    private final Map<String, Token> tokenUtilMap;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("Jwt logout");

        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("access Token {}", accessToken);
        tokenUtilMap.get(TokenConst.ACCESS_TOKEN.getValue())
                .remove(accessToken);

        String refreshToken = request.getHeader(HeaderConst.REFRESH_TOKEN.getValue());
        log.info("refresh Token {}", refreshToken);
        tokenUtilMap.get(TokenConst.REFRESH_TOKEN.getValue())
                .remove(refreshToken);
    }
}
