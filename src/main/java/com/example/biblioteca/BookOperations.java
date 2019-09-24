package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class BookOperations {
    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonProperty
    String type;

    @JsonProperty
    private String issued_to;

    @ManyToOne(optional = false)
    @JsonIgnore
    @JoinColumn(name = "book_id")
    Book book;

    BookOperations(Long id, String type, String issued_to, Book book) {
        this.id = id;
        this.type = type;
        this.issued_to = issued_to;
        this.book = book;
    }

    BookOperations() {
    }

    BookOperations(String type) {
        this.type = type;
    }

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookOperations that = (BookOperations) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    String getIssued_to() {
        return issued_to;
    }

    void setIssued_to(String issued_to) {
        this.issued_to = issued_to;
    }

    void processCheckOut(Book book) {
        this.book = book;
        setType("CHECKOUT");
        String username = getUserNameFromSpringSecurity();
        setIssued_to(username);
    }

    void processCheckIn(Book book) {
        this.book = book;
        setType("AVAILABLE");
        String username = getUserNameFromSpringSecurity();
        setIssued_to(username);
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

    boolean isDifferentUser() {
        String currentLoggedUser = getUserNameFromSpringSecurity();
        return !currentLoggedUser.equals(getIssued_to());
    }
}
