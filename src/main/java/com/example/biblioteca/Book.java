package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Book {
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

    Book(Long id, String isbn, String title, String author, String published_year, String publisher, String checkout_status) {
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

    @JsonIgnore
    Long getId() {
        return id;
    }


    public void setCheckout_status(String checkout_status) {
        this.checkout_status = checkout_status;
    }

    String getCheckout_status()
    {
        return checkout_status;
    }

     boolean checkout(Book bookBeforeCheckoutStatusChange) {

         return this.checkout_status.equals("CHECKEDOUT") && bookBeforeCheckoutStatusChange.checkout_status.equals("AVAILABLE");
     }


}
