package com.example.reactivedemo.service.impl;

import com.example.reactivedemo.domain.Tweet;
import com.example.reactivedemo.repository.TweetRepository;
import com.example.reactivedemo.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository repository;

    @Override
    public Flux<Tweet> saveTweets(Flux<Tweet> tweets) {
        return repository.saveAll(tweets);
    }

    @Override
    public Flux<Tweet> getTweets() {
        return repository.findWithTailableCursorBy();
    }
}
