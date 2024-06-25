package com.mosinsa.gateway.config;

import com.mosinsa.gateway.filter.AuthorizationHeaderFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final AuthorizationHeaderFilter authorizationHeaderFilter;

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

        GatewayFilter headerFilter = authorizationHeaderFilter.apply(new AuthorizationHeaderFilter.Config());

        return builder.routes()
                .route(r -> r.path("/customer-service/login")
                        .and().method(HttpMethod.POST)
                        .filters(f -> f.rewritePath("/customer-service/(?<segment>.*)", REPLACE))
                        .uri("lb://CUSTOMER-SERVICE")
                )
                .route(r -> r.path("/customer-service/customers")
                        .and().method(HttpMethod.POST)
                        .filters(f -> f.rewritePath("/customer-service/(?<segment>.*)", REPLACE))
                        .uri("lb://CUSTOMER-SERVICE")
                )
                .route(r -> r.path("/customer-service/**")
                        .filters(f -> f.rewritePath("/customer-service/(?<segment>.*)", REPLACE)
                                .filters(headerFilter))
                        .uri("lb://CUSTOMER-SERVICE")
                )
                .route(r -> r.path("/product-service/**").and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.rewritePath("/product-service/(?<segment>.*)", REPLACE)
                                .filter(headerFilter))
                        .uri("lb://PRODUCT-SERVICE")
                )
                .route(r -> r.path("/product-service/**").and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.rewritePath("/product-service/(?<segment>.*)", REPLACE))
                        .uri("lb://PRODUCT-SERVICE")
                )
				.route(r -> r.path("/websocket-service/**")
						.filters(f -> f.rewritePath("/websocket-service/(?<segment>.*)", REPLACE))
						.uri("lb://WEBSOCKET-SERVICE")
				)
                .route(r -> r.path("/order-service/**")
                        .filters(f -> f.rewritePath("/order-service/(?<segment>.*)", REPLACE)
                                .filters(headerFilter))
                        .uri("lb://ORDER-SERVICE")
                )
                .route(r -> r.path("/coupon-service/**").and()
                        .method(HttpMethod.POST)
                        .filters(f -> f.rewritePath("/coupon-service/(?<segment>.*)", REPLACE)
                                .filters(headerFilter))
                        .uri("lb://COUPON-SERVICE")
                )
                .route(r -> r.path("/coupon-service/promotions/**").and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.rewritePath("/coupon-service/(?<segment>.*)", REPLACE))
                        .uri("lb://COUPON-SERVICE")
                )
                .route(r -> r.path("/coupon-service/**").and()
                        .method(HttpMethod.GET)
                        .filters(f -> f.rewritePath("/coupon-service/(?<segment>.*)", REPLACE)
                                .filters(headerFilter))
                        .uri("lb://COUPON-SERVICE")
                )
                .build();
    }
}
