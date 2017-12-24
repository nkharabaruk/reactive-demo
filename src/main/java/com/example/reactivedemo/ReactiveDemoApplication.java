package com.example.reactivedemo;

import com.example.reactivedemo.handler.TwitHandler;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactiveDemoApplication extends AbstractReactiveMongoConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveDemoApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> twitRouter(TwitHandler handler) {
		return RouterFunctions.route(GET("/twits"), handler::getAll);
	}

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Override
    protected String getDatabaseName() {
        return "twitter";
    }
}
