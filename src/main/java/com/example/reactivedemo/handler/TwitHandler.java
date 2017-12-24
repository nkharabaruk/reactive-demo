package com.example.reactivedemo.handler;

import com.example.reactivedemo.domain.Twit;
import com.example.reactivedemo.repository.TwitRepository;
import com.example.reactivedemo.service.TwitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class TwitHandler {
    private static int counter = 0;

    @Autowired
    private TwitRepository repository;
    @Autowired
    private TwitService service;

    public Mono<ServerResponse> getAll(ServerRequest request) {
        service.getHomeTimeLine();
        Flux<Twit> twits = Flux.generate(synchronousSink -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronousSink.next(new Twit("new twit #" + counter++));
        });

        return ServerResponse.ok().contentType(MediaType.APPLICATION_STREAM_JSON).body(twits, Twit.class);
    }
}
