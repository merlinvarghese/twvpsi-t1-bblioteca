package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "movie_operations")
class MovieOperations {
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
    @JoinColumn(name = "movie_id")
    Movie movie;

    MovieOperations(Long id, String type, String issued_to, Movie movie) {
        this.id = id;
        this.type = type;
        this.issued_to = issued_to;
        this.movie = movie;
    }

    MovieOperations() {
    }

    MovieOperations(String type) {
        this.type = type;
    }

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    String getIssued_to() {
        return issued_to;
    }

    void setIssued_to(String issued_to) {
        this.issued_to = issued_to;
    }

    void processCheckOut(Movie movie) {
        this.movie = movie;
        setType("CHECKOUT");
        String username = getUserNameFromSpringSecurity();
        setIssued_to(username);
    }

    void processCheckIn(Movie movie) {
        this.movie = movie;
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
