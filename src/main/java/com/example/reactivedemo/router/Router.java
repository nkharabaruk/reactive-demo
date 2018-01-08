package com.example.reactivedemo.router;

import com.example.reactivedemo.handler.TweetHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Component
public class Router {

    private final TweetHandler handler;

    @Autowired
    public Router(TweetHandler handler) {
        this.handler = handler;
    }

    @Bean
    RouterFunction<ServerResponse> tweetRouter() {
        return RouterFunctions.route(GET("/tweets"), handler::getAll);
    }
}
