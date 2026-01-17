package com.theonova.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class HealthHandler {

    public ServerResponse responseHealth(ServerRequest request) {
        return ServerResponse.ok().body("UP");
    }
}
