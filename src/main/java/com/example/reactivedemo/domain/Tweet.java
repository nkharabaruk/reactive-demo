package com.example.reactivedemo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Document(collection = "tweets")
public class Tweet {
    @Id
    @JsonIgnore
    private String id;

    @NonNull
    private String text;

    @NonNull
    private Date date;

    @NonNull
    private String author;

    @NonNull
    private List<String> hashTags;
}