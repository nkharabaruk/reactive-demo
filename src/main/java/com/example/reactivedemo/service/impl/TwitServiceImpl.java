package com.example.reactivedemo.service.impl;

import com.example.reactivedemo.domain.Twit;
import com.example.reactivedemo.repository.TwitRepository;
import com.example.reactivedemo.service.TwitService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TwitServiceImpl implements TwitService {

    @Autowired
    private TwitRepository repository;

    @Override
    @SneakyThrows
    public List<Twit> getHomeTimeLine() {
        Twitter twitter = new TwitterFactory().getInstance();
        List<Twit> twits = twitter.getHomeTimeline().stream().map(this::toTwit).collect(Collectors.toList());
        repository.saveAll(twits);
        return twits;
    }

    private Twit toTwit(Status status) {
        return new Twit(status.getId(), status.getText(), status.getCreatedAt(), status.getUser().getName());
    }

    @Override
    @SneakyThrows
    public List<Status> getUserTimeLine(String screenName) {
        return new TwitterFactory().getInstance().getUserTimeline(screenName, new Paging(5));
    }
}
