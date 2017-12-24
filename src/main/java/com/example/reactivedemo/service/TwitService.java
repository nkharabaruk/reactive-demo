package com.example.reactivedemo.service;

import com.example.reactivedemo.domain.Twit;
import twitter4j.Status;

import java.util.List;

public interface TwitService {

    List<Twit> getHomeTimeLine();

    List<Status> getUserTimeLine(String screenName);
}
