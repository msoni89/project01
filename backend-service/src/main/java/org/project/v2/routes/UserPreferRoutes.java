package org.project.v2.routes;

import org.project.v2.handlers.UserPreferenceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.*;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Component
public class UserPreferRoutes {

    @Bean
    public RouterFunction<ServerResponse> userPreferenceRoutes(UserPreferenceHandler preferenceHandler) {
        return route(POST("/api/v2/user-preferences"), preferenceHandler::create)
                .andRoute(GET("/api/v2/user-preferences/id/{id}"), preferenceHandler::getById)
                .andRoute(PUT("/api/v2/user-preferences/id/{id}"), preferenceHandler::update);
    }
}
