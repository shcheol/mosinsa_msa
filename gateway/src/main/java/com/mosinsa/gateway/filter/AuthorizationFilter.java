package com.mosinsa.gateway.filter;

import com.mosinsa.gateway.jwt.Token;
import com.mosinsa.gateway.jwt.TokenConst;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {
    private final Map<String, Token> tokenUtilMap;
    private final Set<String> whiteList = new HashSet<>();
    private static final String[] AUTH_WHITELIST = {
            "/customers", "/login",
            "/actuator/prometheus"
    };

    @PostConstruct
    void init() {
        whiteList.addAll(Arrays.stream(AUTH_WHITELIST).toList());
    }

    @Override
    public GatewayFilter apply(AuthorizationFilter.Config config) {

        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("request path [{}]", request.getPath());

            if (whiteList.contains(request.getPath().toString())/* && request.getMethod() == HttpMethod.POST*/) {
                return chain.filter(exchange);
            }

            HttpHeaders headers = request.getHeaders();

            Optional<String> authorizationHeader = Objects.requireNonNull(
                            request.getHeaders().get(HttpHeaders.AUTHORIZATION)).stream()
                    .filter(f -> f.startsWith("Bearer"))
                    .map(b -> b.replace("Bearer", ""))
                    .findAny();
            if (authorizationHeader.isEmpty()) {
                return onError(exchange, AuthorizationError.EMPTY_AUTHORIZATION_TOKEN);
            }

            String accessToken = authorizationHeader.get();
            log.info("access token: {}", accessToken);

            Token accessTokenUtil = tokenUtilMap.get(TokenConst.ACCESS_TOKEN.getValue());
            if (accessTokenUtil.isValid(accessToken)) {
                return chain.filter(exchange);
            }

            Optional<String> refreshTokenHeader = Objects.requireNonNull(headers.get(HeaderConst.REFRESH_TOKEN.getValue())).stream().findAny();
            if (refreshTokenHeader.isEmpty()) {
                return onError(exchange, AuthorizationError.EMPTY_AUTHORIZATION_TOKEN);
            }
            String refreshToken = refreshTokenHeader.get();
            log.info("refresh token: {}", refreshToken);

            Token refreshTokenUtil = tokenUtilMap.get(TokenConst.REFRESH_TOKEN.getValue());
            if (refreshTokenUtil.isValid(refreshToken)) {
                String newAccessToken = accessTokenUtil.create(refreshTokenUtil.getSubject(refreshToken));
                exchange.getResponse().getHeaders().add(HeaderConst.ACCESS_TOKEN.getValue(), newAccessToken);
                return chain.filter(exchange);
            }

            return onError(exchange, AuthorizationError.JWT_VALID_ERROR);
        });
    }

    @Getter
    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, AuthorizationError error) {
        log.error(error.getMessage());

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(error.getStatus());
        return response.setComplete();
    }
}
