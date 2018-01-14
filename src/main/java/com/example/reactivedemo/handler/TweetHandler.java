package com.example.reactivedemo.handler;

import com.example.reactivedemo.domain.Tweet;
import com.example.reactivedemo.service.TweetService;
import com.example.reactivedemo.service.TwitterService;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TweetHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TweetHandler.class);

    private final TweetService tweetService;
    private final TwitterService twitterService;

    private Map<Long, Subscription> subscriptions = new HashMap<>();

    @Autowired
    public TweetHandler(TweetService tweetService, TwitterService twitterService) {
        this.tweetService = tweetService;
        this.twitterService = twitterService;
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        long timestamp = System.currentTimeMillis();
        List<String> tags = request.queryParam("tags")
                .map(str -> Arrays.stream(str.split(","))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        Flux<Tweet> tweets;
        if (tags.isEmpty()) {
            tweets = tweetService.getTweets();
        } else {
            tweets = tweetService.getTweetsWithTags(tags);
        }
        tweets = tweets.doOnSubscribe(subscription -> {
            if (CollectionUtils.isEmpty(subscriptions)) {
                twitterService.startStreaming();
            }
            subscriptions.put(timestamp, subscription);
            LOGGER.info("new client subscribed at " + new Date(timestamp));
        }).doOnCancel(() -> {
            subscriptions.remove(timestamp);
            LOGGER.info("new client unsubscribed at " + new Date(timestamp));
            if (CollectionUtils.isEmpty(subscriptions)) {
                twitterService.stopStreaming();
            }
        }).doOnComplete(() -> {
            subscriptions.remove(timestamp);
            LOGGER.info("new client finished at " + new Date(timestamp));
            if (CollectionUtils.isEmpty(subscriptions)) {
                twitterService.stopStreaming();
            }
        });
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(tweets, Tweet.class);
    }
}
