package com.mosinsa.customer.ui.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.customer.common.ex.CustomerError;
import com.mosinsa.customer.common.ex.CustomerException;
import com.mosinsa.customer.application.CustomerService;
import com.mosinsa.customer.common.jwt.Token;
import com.mosinsa.customer.common.jwt.TokenConst;
import com.mosinsa.customer.ui.request.LoginRequest;
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
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final ObjectMapper om = new ObjectMapper();
    private final CustomerService customerService;

    private final Map<String, Token> tokenUtilMap;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequest credentials = om.readValue(request.getInputStream(), LoginRequest.class);

            UsernamePasswordAuthenticationToken authentication
                    = new UsernamePasswordAuthenticationToken(credentials.getLoginId(), credentials.getPassword(), new ArrayList<>());
            return getAuthenticationManager().authenticate(
                    authentication);
        } catch (IOException e) {
            throw new CustomerException(CustomerError.WRONG_ID_OR_PASSWORD, e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        String id = customerService.findByLoginId(((User) authResult.getPrincipal()).getUsername()).getId().getId();

        response.addHeader(HeaderConst.CUSTOMER_ID.getValue(), id);
        response.addHeader(HeaderConst.ACCESS_TOKEN.getValue(),
                tokenUtilMap.get(TokenConst.ACCESS_TOKEN.getValue()).create(id));
        response.addHeader(HeaderConst.REFRESH_TOKEN.getValue(),
                tokenUtilMap.get(TokenConst.REFRESH_TOKEN.getValue()).create(id));
    }
}