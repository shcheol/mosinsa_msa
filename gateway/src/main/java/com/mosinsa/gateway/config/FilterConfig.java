package com.mosinsa.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/customer-service/**")
//                        .filters(f -> f.addRequestHeader("gateway","gateway")
//                                .addResponseHeader("gateway","gateway"))
                        .uri("lb://CUSTOMER-SERVICE")
                )
                .route(r -> r.path("/product-service/**")
//                        .filters(f -> f.addRequestHeader("gateway","gateway")
//                                .addResponseHeader("gateway","gateway"))
                        .uri("lb://PRODUCT-SERVICE")
                )
                .build();
    }
}
