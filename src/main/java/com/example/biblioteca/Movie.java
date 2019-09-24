package com.example.biblioteca;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unused")
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private final Long id;

    @JsonProperty
    private final String name;

    @JsonProperty
    private final String year;

    @JsonProperty
    private final String director;

    @JsonProperty
    private final String rating;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "movie")
    private List<MovieOperations> operations;

    public Movie() {
        this.id = null;
        this.name = null;
        this.year = null;
        this.director = null;
        this.rating = null;
        this.operations = null;
    }

    @JsonIgnore
    Long getId() {
        return id;
    }

    Movie(long id, String name, String year, String director, String rating) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.director = director;
        this.rating = rating;
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

        if (!(other instanceof Movie)) {
            return false;
        }

        Movie movie = (Movie) other;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    void checkOut(MovieOperations movieOperation) {
        movieOperation.processCheckOut(this);
        operations.add(movieOperation);
    }

    void returnMovie(MovieOperations movieOperation) {
        movieOperation.processCheckIn(this);
        operations.add(movieOperation);
    }
}
