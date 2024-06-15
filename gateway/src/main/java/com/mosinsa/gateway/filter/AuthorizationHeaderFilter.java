package com.mosinsa.gateway.filter;

import com.mosinsa.gateway.jwt.Token;
import com.mosinsa.gateway.jwt.TokenMapEnums;
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

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {
    private final Map<String, Token> tokenUtilMap;

    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("request path [{}]", request.getPath());

            HttpHeaders headers = request.getHeaders();

            Optional<String> authorizationHeader =
                    Optional.ofNullable(request.getHeaders().get(HttpHeaders.AUTHORIZATION))
                            .orElseGet(Collections::emptyList).stream()
                            .filter(f -> f.startsWith("Bearer"))
                            .map(b -> b.replace("Bearer", ""))
                            .findAny();
            if (authorizationHeader.isEmpty()) {
                return onError(exchange, AuthorizationError.EMPTY_AUTHORIZATION_TOKEN);
            }

            String accessToken = authorizationHeader.get();
            log.info("access token: {}", accessToken);

            Token accessTokenUtil = tokenUtilMap.get(TokenMapEnums.ACCESS_TOKEN.key());
            if (accessTokenUtil.isValid(accessToken)) {
                return chain.filter(exchange);
            }

            Optional<String> refreshTokenHeader =
                    Optional.ofNullable(headers.get(HeaderConst.REFRESH_TOKEN.key()))
                            .orElseGet(Collections::emptyList).stream().findAny();
            if (refreshTokenHeader.isEmpty()) {
                return onError(exchange, AuthorizationError.EMPTY_AUTHORIZATION_TOKEN);
            }
            String refreshToken = refreshTokenHeader.get();
            log.info("refresh token: {}", refreshToken);

            Token refreshTokenUtil = tokenUtilMap.get(TokenMapEnums.REFRESH_TOKEN.key());
            if (refreshTokenUtil.isValid(refreshToken)) {
                String newAccessToken = accessTokenUtil.create(refreshTokenUtil.getSubject(refreshToken));
                exchange.getResponse().getHeaders().add(HeaderConst.ACCESS_TOKEN.key(), newAccessToken);
                return chain.filter(exchange);
            }

            return onError(exchange, AuthorizationError.JWT_VALID_ERROR);
        });
    }

    public static class Config {
    }

    private Mono<Void> onError(ServerWebExchange exchange, AuthorizationError error) {
        log.error(error.getMessage());

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(error.getStatus());
        return response.setComplete();
    }
}
