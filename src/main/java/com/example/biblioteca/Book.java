package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private final Long id;

    @SuppressWarnings("unused")
    @JsonProperty
    private final String isbn;

    @SuppressWarnings("unused")
    @JsonProperty
    private final String title;

    @SuppressWarnings("unused")
    @JsonProperty
    private final String author;

    @SuppressWarnings("unused")
    @JsonProperty
    private final String published_year;

    @SuppressWarnings("unused")
    @JsonProperty
    private final String publisher;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "book")
    private List<BookOperations> operations;

    @SuppressWarnings("unused")
    Book() {
        this.id = null;
        this.isbn = null;
        this.title = null;
        this.author = null;
        this.published_year = null;
        this.publisher = null;
        this.operations = null;
    }

    Book(Long id, String isbn, String title, String author
            , String published_year, String publisher) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.published_year = published_year;
        this.publisher = publisher;
        this.operations = null;
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

    void checkOut(BookOperations bookOperations) {
        bookOperations.processCheckOut(this);
        operations.add(bookOperations);
    }

    void returnBook(BookOperations movieOperation) {
        movieOperation.processCheckIn(this);
        operations.add(movieOperation);
    }

    @JsonIgnore
    Long getId() {
        return id;
    }


    private String getUserNameFromSpringSecurity() {
        String username = "";
        try {
            Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = ((User) user).getUsername();
        } catch (Exception e) {
        }

        return username;
    }

}
