package org.project.v2.routes;

import org.project.v2.handlers.UISelectorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Component
public class UIBuilderRoutes {

    @Bean
    public RouterFunction<ServerResponse> uiBuilderRoutes(UISelectorHandler selectorHandler) {
        return route(GET("/api/v2/ui-builder/selectors"), selectorHandler::all);
    }
}
