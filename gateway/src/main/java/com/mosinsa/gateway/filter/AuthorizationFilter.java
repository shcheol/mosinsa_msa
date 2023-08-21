package com.mosinsa.gateway.filter;

import com.mosinsa.gateway.jwt.JwtUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

	private final Environment env;
	private final JwtUtils utils;

	@Override
	public GatewayFilter apply(AuthorizationFilter.Config config) {


		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			HttpHeaders headers = request.getHeaders();

			if (!headers.containsKey(HttpHeaders.AUTHORIZATION)){
				return onError(exchange, "no authorization header", HttpStatus.UNAUTHORIZED);
			}

			String authorizationHeader = headers.get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authorizationHeader.replace("Bearer", "");

			if(!utils.isValid(jwt)){
				return onError(exchange, "jwt token is not valid", HttpStatus.UNAUTHORIZED);
			}

			return chain.filter(exchange);
		});
	}

	@Getter
	public static class Config {
	}

	private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status){
		log.error(message);

		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);

		return response.setComplete();
	}
}
