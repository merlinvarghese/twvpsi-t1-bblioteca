package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonProperty;

class Book {
    @JsonProperty
    private final long id;

    @JsonProperty
    private final String name;

    @JsonProperty
    private final String author;

    @JsonProperty
    private final String yearPublished;

    public Book(long id, String name, String author, String yearPublished) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.yearPublished = yearPublished;
    }
}
