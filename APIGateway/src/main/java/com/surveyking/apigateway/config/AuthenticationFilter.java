package com.surveyking.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GatewayFilter {
    private final RouterValidator routerValidator;
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String authHeader = getAuthHeader(request);
            final String jwt = authHeader.substring(7);

            try {
                if (jwtUtil.isInvalid(jwt)) {
                    return onError(exchange, HttpStatus.FORBIDDEN);
                }else{
                    updateRequest(exchange, jwt);
                }
            } catch (Exception e) {
                return onError(exchange, HttpStatus.FORBIDDEN);
            }

        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private void updateRequest(ServerWebExchange exchange, String token) throws Exception {
            exchange.getRequest().mutate()
                    .header("userId", String.valueOf(jwtUtil.extractUserId(token)))
                    .header("authorities", jwtUtil.extractUserAuthorities(token))
                    .build();
    }
}
