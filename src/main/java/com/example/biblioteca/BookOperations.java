package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class BookOperations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private final Long id;

    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "book_id")
    Book book;

    private final String issued_to;

    private final String status;

     BookOperations(Book book, String issued_to, String status) {
         this.id = null;
         this.book = book;
        this.issued_to = issued_to;
        this.status = status;
    }

    BookOperations()
    {
        this.id = null;
        this.book = null;
        this.status = null;
        this.issued_to = null;

    }


    public Long getId() {
        return id;
    }

    public String getIssued_to() {
        return issued_to;
    }

    public String getStatus() {
        return status;
    }

    public boolean isDifferentUser() {
    }
}
