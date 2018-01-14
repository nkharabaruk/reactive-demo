package com.example.reactivedemo.repository;

import com.example.reactivedemo.domain.Tweet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

@Repository
public interface TweetRepository extends ReactiveMongoRepository<Tweet, String> {

    @Tailable
    Flux<Tweet> findWithTailableCursorBy();

    @Tailable
    Flux<Tweet> findByHashTagsIn(List<String> tags);
}
