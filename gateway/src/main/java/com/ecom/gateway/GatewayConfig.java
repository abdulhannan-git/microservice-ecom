package com.ecom.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Component
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("eureka", r -> r.path("/eureka/main")
                        .filters(f -> f.rewritePath("/eureka/main", ""))
                        .uri("http://localhost:8761"))
                .route("eureka-static", r -> r.path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }
}

