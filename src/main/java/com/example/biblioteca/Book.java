package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
class Book {

    private final String CHECKOUT_SUCCESS = "Thank you! Enjoy the book.";
    private final String CHECKOUT_FAILURE = "That book is not available.";
    private final String CHECKIN_SUCCESS = "Thank you for returning the book.";
    private final String CHECKIN_FAILURE = "That is not a valid book to return.";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookOperationsRepository bookOperationsRepository;


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
    }

    Book(Long id, String isbn, String title, String author
            , String published_year, String publisher, String checkout_status) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.published_year = published_year;
        this.publisher = publisher;
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

    Messages checkOut(BookOperations currentBookOperation) {
        Messages message = new Messages();
        Long lastBookOperationId = bookOperationsRepository.getLastBookOperationId(this.id);
        if (lastBookOperationId != null) {
            BookOperations bookOperations = bookOperationsRepository.findById(lastBookOperationId).get();
            if (bookOperations.getStatus().equals("CHECKEOUT")) {
                message.setMessage(CHECKOUT_FAILURE);
                return message;
            }
        }
        BookOperations bookOperations = new BookOperations(this, getUserNameFromSpringSecurity(), "CHECKEDOUT");
        this.operations.add(bookOperations);
        message.setMessage(CHECKOUT_SUCCESS);
        return message;
    }

    Messages checkIn() {
        Messages message = new Messages();
        Long lastBookOperationId = bookOperationsRepository.getLastBookOperationId(this.id);
        if (lastBookOperationId != null) {
            BookOperations bookOperations = bookOperationsRepository.findById(lastBookOperationId).get();
            if (bookOperations.getStatus().equals("AVAILABLE")) {
                message.setMessage(CHECKIN_FAILURE);
                return message;
            }
        }
        BookOperations bookOperations = new BookOperations(this, getUserNameFromSpringSecurity(), "AVAILABLE");
        this.operations.add(bookOperations);
        bookRepository.save(this);
        message.setMessage(CHECKIN_SUCCESS);
        return message;
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

    public List<BookOperations> getOperations() {
        return operations;
    }


    public void returnMovie(BookOperations currentMovieOperation) {
    }
}
