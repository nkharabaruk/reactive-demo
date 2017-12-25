package com.example.reactivedemo.handler;

import com.example.reactivedemo.domain.Tweet;
import com.example.reactivedemo.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TweetHandler {

    private TweetService tweetService;

    @Autowired
    public TweetHandler(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        Flux<Tweet> tweets = tweetService.getTweets();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(tweets, Tweet.class);
    }
}
