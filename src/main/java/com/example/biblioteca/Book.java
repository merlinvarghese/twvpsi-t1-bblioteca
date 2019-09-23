package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String checkout_status;

    @SuppressWarnings("unused")
    Book() {
        this.id = null;
        this.isbn = null;
        this.title = null;
        this.author = null;
        this.published_year = null;
        this.publisher = null;
        this.checkout_status = null;
    }

    Book(Long id, String isbn, String title, String author
            , String published_year, String publisher, String checkout_status) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.published_year = published_year;
        this.publisher = publisher;
        this.checkout_status = checkout_status;
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

    boolean checkOut() {
        if (checkout_status.equals("CHECKEDOUT")) {
            return false;
        }
        setCheckout_status("CHECKEDOUT");
        return true;
    }

    boolean checkIn() {
        if (checkout_status.equals("AVAILABLE")) {
            return false;
        }
        setCheckout_status("AVAILABLE");
        return true;
    }

    void setCheckout_status(String checkout_status) {
        this.checkout_status = checkout_status;
    }

    String getCheckout_status() {
        return checkout_status;
    }

    @JsonIgnore
    Long getId() {
        return id;
    }
}
