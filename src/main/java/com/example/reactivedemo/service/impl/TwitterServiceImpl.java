package com.example.reactivedemo.service.impl;

import com.example.reactivedemo.domain.Tweet;
import com.example.reactivedemo.repository.TweetRepository;
import com.example.reactivedemo.service.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.StatusAdapter;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class TwitterServiceImpl implements TwitterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterServiceImpl.class);

    private final TwitterStream twitterStream;
    private final StatusAdapter listener;

    @Autowired
    public TwitterServiceImpl(TweetRepository repository) {
        twitterStream = new TwitterStreamFactory().getInstance();
        listener = new StatusAdapter() {
            @Override
            public void onStatus(Status status) {
                repository
                        .save(new Tweet(
                                status.getText(),
                                status.getCreatedAt(),
                                status.getUser().getName(),
                                Arrays.stream(status.getHashtagEntities())
                                        .map(tag -> tag.getText().toLowerCase())
                                        .collect(Collectors.toList())))
                        .subscribe();
                LOGGER.info("new Tweet saved to DB");
            }
        };
    }

    @Override
    public void startStreaming() {
        twitterStream.addListener(listener);
        twitterStream.filter("#java", "#scala", "#kotlin", "#groovy");
        LOGGER.info("Start streaming Twitter");
    }

    @Override
    public void stopStreaming() {
        if (twitterStream != null) {
            twitterStream.removeListener(listener);
            LOGGER.info("Stop streaming Twitter");
        }
    }
}
