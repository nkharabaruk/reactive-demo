package com.example.reactivedemo.repository;

import com.example.reactivedemo.domain.Twit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitRepository extends ReactiveMongoRepository<Twit, String> {

}
