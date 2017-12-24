package com.example.reactivedemo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Document
@AllArgsConstructor
public class Twit {

    @Id
    private Long id;

    private String text;

    private Date date;

    private String author;

    public Twit(String text) {
        this.author = UUID.randomUUID().toString();
        this.text = text;
    }
}
