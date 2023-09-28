package com.mosinsa.gateway.filter;

import com.mosinsa.gateway.jwt.JwtUtils;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
	private final JwtUtils utils;
	private final Set<String> whiteList = new HashSet<>();
	private static final String[] AUTH_WHITELIST = {
			"/customers", "/login",
			"/customer-service/actuator/prometheus",
			"/order-service/actuator/prometheus",
			"/product-service/actuator/prometheus"
	};

	@PostConstruct
	void init(){
		whiteList.addAll(Arrays.stream(AUTH_WHITELIST).toList());
	}

	@Override
	public GatewayFilter apply(AuthorizationFilter.Config config) {

		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			log.info("request path [{}]", request.getPath());

			if (whiteList.contains(request.getPath().toString())/* && request.getMethod() == HttpMethod.POST*/){
				return chain.filter(exchange);
			}

			HttpHeaders headers = request.getHeaders();
			for (String h : headers.keySet()) {
				System.out.println("h = " + h);
				System.out.println("headers.get(h) = " + headers.get(h));
			}
			if (!headers.containsKey(HttpHeaders.AUTHORIZATION)){
				return onError(exchange, AuthorizationError.EMPTY_AUTHORIZATION_TOKEN);
			}
			Optional<String> token = Objects.requireNonNull(headers.get(HttpHeaders.AUTHORIZATION)).stream()
					.filter(f -> f.startsWith("Bearer")).findFirst();
			if (token.isEmpty()){
				return onError(exchange, AuthorizationError.EMPTY_AUTHORIZATION_TOKEN);
			}

			String accessToken = token.get().replace("Bearer", "");
			log.info("access token: {}", accessToken);

			if(!utils.isAccessTokenValid(accessToken)){
				List<String> refreshTokenHeader = headers.get(HeaderConst.REFRESH_TOKEN.getName());
				if(refreshTokenHeader == null || refreshTokenHeader.isEmpty()){
					return onError(exchange, AuthorizationError.EMPTY_AUTHORIZATION_TOKEN);
				}
				String refreshToken = headers.get(HeaderConst.REFRESH_TOKEN.getName()).get(0);

				if (!utils.isRefreshTokenValid(refreshToken)){
					return onError(exchange, AuthorizationError.JWT_VALID_ERROR);
				}

				String newAccessToken = utils.createAccessToken(utils.getSubject(refreshToken));
				exchange.getResponse().getHeaders().add(HeaderConst.ACCESS_TOKEN.getName(), newAccessToken);
			}

			return chain.filter(exchange);
		});
	}

	@Getter
	public static class Config {
	}

	private Mono<Void> onError(ServerWebExchange exchange, AuthorizationError error){
		log.error(error.getMessage());

		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(error.getStatus());
		return response.setComplete();
	}
}
