package com.example.reactivedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactiveDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveDemoApplication.class, args);
    }

//    @Override
//    public MongoClient reactiveMongoClient() {
//        MongoClient client = MongoClients.create("mongodb://localhost:27017");
//        CreateCollectionOptions options = new CreateCollectionOptions();
//        options.capped(true);
//        options.maxDocuments(100);
//        options.autoIndex(true);
////        client.getDatabase(DB_NAME).getCollection("tweets").drop();
//        client.getDatabase(DB_NAME).createCollection(
//                "tweets",
//                options
//        );
//        return client;
//    }
}
