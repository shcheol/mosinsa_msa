package com.mosinsa.customer.ui.filter;

import com.mosinsa.customer.common.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Slf4j
@RequiredArgsConstructor
public class JwtLogoutHandler implements LogoutHandler {

	private final TokenProvider provider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("Jwt logout");

        String refreshToken = request.getHeader(HeaderConst.REFRESH_TOKEN.key());
		provider.remove(refreshToken);
    }
}
