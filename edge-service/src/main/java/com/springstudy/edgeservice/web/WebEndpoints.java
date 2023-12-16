package com.springstudy.edgeservice.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Configuration
public class WebEndpoints {

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction() {
        return RouterFunctions.route()
                .GET("/catalog-fallback", request -> ServerResponse.ok().body(Mono.just("Catalog Fallback"), String.class))
                .POST("/catalog-fallback", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build())
                .GET("/order-fallback", request -> ServerResponse.ok().body(Mono.just("Order Fallback"), String.class))
                .POST("/order-fallback", request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).build())
                .build();
    }
}
