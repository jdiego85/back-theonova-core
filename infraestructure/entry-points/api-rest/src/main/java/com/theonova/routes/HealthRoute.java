package com.theonova.routes;

import com.theonova.handlers.HealthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration
public class HealthRoute {

    @Bean
    public RouterFunction<ServerResponse> healthRouterFunction(HealthHandler handler) {
        return route(GET("/routerhealth"),handler::responseHealth);
    }
}
