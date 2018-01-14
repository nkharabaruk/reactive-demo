package com.example.reactivedemo.service;

import com.example.reactivedemo.domain.Tweet;
import reactor.core.publisher.Flux;

import java.util.List;

public interface TweetService {

    Flux<Tweet> saveTweets(Flux<Tweet> tweets);

    Flux<Tweet> getTweets();

    Flux<Tweet> getTweetsWithTags(List<String> tags);
}
