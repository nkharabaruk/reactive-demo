package com.example.reactivedemo.router;

import com.example.reactivedemo.handler.TwitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Component
public class TwitRouter {

    private final TwitHandler handler;

    @Autowired
    public TwitRouter(TwitHandler handler) {
        this.handler = handler;
    }

    @Bean
    RouterFunction<ServerResponse> twitRouter() {
        return RouterFunctions.route(GET("/twits"), handler::getAll);
    }

}
