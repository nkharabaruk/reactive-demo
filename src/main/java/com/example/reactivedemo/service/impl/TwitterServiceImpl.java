package com.example.reactivedemo.service.impl;

import com.example.reactivedemo.domain.Tweet;
import com.example.reactivedemo.repository.TweetRepository;
import com.example.reactivedemo.service.TwitterService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import twitter4j.*;

import javax.annotation.PostConstruct;

@Service
public class TwitterServiceImpl implements TwitterService {

    TweetRepository repository;

    @Autowired
    public TwitterServiceImpl(TweetRepository repository) {
        this.repository = repository;
    }

    @Override
    @SneakyThrows
    public Flux<Tweet> getHomeTimeLine() {
        Twitter twitter = new TwitterFactory().getInstance();
        return Flux.fromStream(
                twitter.getHomeTimeline().stream()
                        .map(this::toTweet));
    }

    private Tweet toTweet(Status status) {
        return new Tweet(
                status.getText(),
                status.getCreatedAt(),
                status.getUser().getName());
    }

    @Override
    @SneakyThrows
    public Flux<Status> getUserTimeLine(String screenName) {
        return Flux.fromStream(
                new TwitterFactory().getInstance()
                        .getUserTimeline(screenName, new Paging(5))
                        .stream());
    }

    @PostConstruct
    private void asf() {
        startStreaming();
    }

    @Override
    public void startStreaming() {
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.onStatus(status -> {
            repository.save(toTweet(status)).subscribe();
            System.out.println("new Tweet saved to DB");
        });
        twitterStream.filter("java");
    }
}
