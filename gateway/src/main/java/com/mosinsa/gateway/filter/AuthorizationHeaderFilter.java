package com.mosinsa.gateway.filter;

import com.mosinsa.gateway.jwt.TokenValidator;
import com.mosinsa.gateway.jwt.TokenVo;
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
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

	private final TokenValidator tokenValidator;

	@Override
	public GatewayFilter apply(Config config) {

		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			log.info("request path [{}]", request.getPath());
			try {

				String accessToken = Optional.ofNullable(request.getHeaders().get(HttpHeaders.AUTHORIZATION))
						.orElseGet(Collections::emptyList).stream()
						.filter(f -> f.startsWith("Bearer"))
						.map(b -> b.replace("Bearer", "").trim())
						.findAny().orElseThrow(() -> new AuthorizationException(AuthorizationError.EMPTY_AUTHORIZATION_TOKEN));

				String refreshToken = Optional.ofNullable(request.getHeaders().get(HeaderConst.REFRESH_TOKEN.key()))
						.orElseGet(Collections::emptyList).stream().findAny().orElse("");

				TokenVo tokenVo = tokenValidator.validateAndGet(new TokenVo(accessToken, refreshToken));
				exchange.getResponse().getHeaders().add(HeaderConst.ACCESS_TOKEN.key(), tokenVo.accessToken());
				return chain.filter(exchange);
			} catch (AuthorizationException ex) {
				return onError(exchange, ex.getError());
			}
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
