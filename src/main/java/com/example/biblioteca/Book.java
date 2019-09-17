package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @JsonProperty
    private String isbn;

    @JsonProperty
    private String title;

    @JsonProperty
    private String author;

    @JsonProperty
    private String published_year;

    @JsonProperty
    private String publisher;

    public Book(Long id, String isbn, String title, String author, String published_year, String publisher) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.published_year = published_year;
        this.publisher = publisher;
    }

    public Book() {
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

        if (getClass() != other.getClass()) {
            return false;
        }

        Book book = (Book) other;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublished_year() {
        return published_year;
    }

    public String getPublisher() {
        return publisher;
    }
}
