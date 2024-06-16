package com.mosinsa.customer.ui.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mosinsa.customer.common.ex.CustomerError;
import com.mosinsa.customer.common.ex.CustomerException;
import com.mosinsa.customer.application.CustomerService;
import com.mosinsa.customer.common.jwt.Token;
import com.mosinsa.customer.common.jwt.TokenMapEnums;
import com.mosinsa.customer.domain.Customer;
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

import static com.mosinsa.customer.ui.filter.HeaderConst.*;


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

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            credentials.getLoginId(),
                            credentials.getPassword(),
                            new ArrayList<>()
                    );

            return getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new CustomerException(CustomerError.WRONG_ID_OR_PASSWORD, e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {

        Customer customerDetail = customerService.findByLoginId(((User) authResult.getPrincipal()).getUsername());
        String id = customerDetail.getId().getId();
        String name = customerDetail.getName();

        Map<String, String> customerInfo = Map.of("id",id,"name",name);
        String infoJson = "";
        try {
            infoJson = om.writeValueAsString(customerInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        response.addHeader(CUSTOMER_INFO.key(), infoJson);
        response.addHeader(ACCESS_TOKEN.key(), tokenUtilMap.get(TokenMapEnums.ACCESS_TOKEN.key()).create(id));
        response.addHeader(REFRESH_TOKEN.key(), tokenUtilMap.get(TokenMapEnums.REFRESH_TOKEN.key()).create(id));
    }
}