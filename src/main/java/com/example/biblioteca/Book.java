package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private final Long id;

    @JsonProperty
    private final String isbn;

    @JsonProperty
    private final String title;

    @JsonProperty
    private final String author;

    @JsonProperty
    private final String published_year;

    @JsonProperty
    private final String publisher;

    public Book() {
        this.id = null;
        this.isbn = null;
        this.title = null;
        this.author = null;
        this.published_year = null;
        this.publisher = null;
    }

    public Book(Long id, String isbn, String title, String author, String published_year, String publisher) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.published_year = published_year;
        this.publisher = publisher;
    }
}
