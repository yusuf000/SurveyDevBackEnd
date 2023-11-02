package com.surveyking.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authentication-service", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://AUTHENTICATION-SERVICE"))
                .route("survey-service", r -> r.path("/api/v1/answer/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://SURVEY-SERVICE"))
                .route("question-service", r -> r.path(
                                "/api/v1/choice/**",
                                "/api/v1/choice-filter/**",
                                "/api/v1/language/**",
                                "/api/v1/project/**",
                                "/api/v1/question/**",
                                "/api/v1/question-filter/**",
                                "/api/v1/question-type/**"
                        )
                        .filters(f -> f.filter(filter))
                        .uri("lb://QUESTION-SERVICE"))
                .build();
    }

}
