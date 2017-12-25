package com.example.reactivedemo.service;

import com.example.reactivedemo.domain.Tweet;
import reactor.core.publisher.Flux;
import twitter4j.Status;

public interface TwitterService {

    Flux<Tweet> getHomeTimeLine();

    Flux<Status> getUserTimeLine(String screenName);

    void startStreaming();
}
