package com.mosinsa.gateway.config;

import com.mosinsa.gateway.filter.AuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FilterConfig {

	private final AuthorizationFilter authorizationFilter;

	private static final String REPLACE = "/$\\{segment}";

	@Bean
	public GlobalFilter globalLogFilter() {

		return (exchange, chain) -> {
			String requestId = UUID.randomUUID().toString().substring(0, 8);
			log.info("[{}] request uri [{}]", requestId, exchange.getRequest().getURI());

			return chain.filter(exchange)
					.then(Mono.fromRunnable(() -> log.info("[{}] end ", requestId)));
		};
	}

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

		GatewayFilter apply = authorizationFilter.apply(new AuthorizationFilter.Config());

		return builder.routes()
				.route(r -> r.path("/customer-service/**")
						.filters(f -> f.rewritePath("/customer-service/(?<segment>.*)", REPLACE)
								.filters(apply))
						.uri("lb://CUSTOMER-SERVICE")
				)
				.route(r -> r.path("/product-service/**")
						.filters(f -> f.rewritePath("/product-service/(?<segment>.*)", REPLACE)
								.filter(apply))
						.uri("lb://PRODUCT-SERVICE")
				)
				.route(r -> r.path("/order-service/**")
						.filters(f -> f.rewritePath("/order-service/(?<segment>.*)", REPLACE)
								.filters(apply))
						.uri("lb://ORDER-SERVICE")
				)
				.build();
	}
}
