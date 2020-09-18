package com.quarkushackfest.team6.domain;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import org.bson.codecs.pojo.annotations.BsonProperty;

@MongoEntity(collection = "Quote")
public class Quote extends ReactivePanacheMongoEntity {
    @BsonProperty("bookId")
    private String id;
    private String book;
    private String content;

    public Quote(String id, String book, String content) {
        this.id = id;
        this.book = book;
        this.content = content;
    }
}
