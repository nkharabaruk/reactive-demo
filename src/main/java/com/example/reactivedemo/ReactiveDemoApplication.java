package com.example.reactivedemo;

import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.mongodb.reactivestreams.client.Success;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.BaseSubscriber;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactiveDemoApplication {

    private static final String DB_NAME = "reactive-mongo";
    private static final String COLLECTION_NAME = "tweets";

    public static void main(String[] args) {
        createCappedCollection();
        SpringApplication.run(ReactiveDemoApplication.class, args);
    }

    @SuppressWarnings("all")
    private static void createCappedCollection() {
        MongoClient client = MongoClients.create();
        MongoDatabase db = client.getDatabase(DB_NAME);
        db.getCollection(COLLECTION_NAME)
                .drop()
                .subscribe(new BaseSubscriber<Success>() {
                    @Override
                    protected void hookOnComplete() {
                        db.createCollection(
                                COLLECTION_NAME,
                                new CreateCollectionOptions()
                                        .capped(true)
                                        .sizeInBytes(100500))
                                .subscribe(new BaseSubscriber<Success>() {
                                    @Override
                                    protected void hookOnComplete() {
                                        client.close();
                                    }
                                });
                    }
                });
    }
}
