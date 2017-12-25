package com.example.reactivedemo.service;

import com.example.reactivedemo.domain.Tweet;
import reactor.core.publisher.Flux;

public interface TweetService {

    Flux<Tweet> saveTweets(Flux<Tweet> tweets);

    Flux<Tweet> getTweets();
}
