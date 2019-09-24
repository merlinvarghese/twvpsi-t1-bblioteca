package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    static final String MOVIE_CHECKOUT_SUCCESS = "Thank you! Enjoy the movie.";
    static final String MOVIE_CHECKOUT_FAIL = "That movie is not available.";
    static final String MOVIE_RETURN_SUCCESS = "Thank you for returning the movie.";
    static final String MOVIE_RETURN_FAIL = "That is not a valid movie to return.";
    static final String MOVIE_RETURN_FAIL_FOR_INVALID_USER = "Invalid user";

    @SuppressWarnings("unused")
    @Autowired
    private MovieRepository movieRepository;

    @SuppressWarnings("unused")
    @Autowired
    private MovieOperationsRepository movieOperationsRepository;

    private List<Movie> getAllMovies() {
        return (List<Movie>) movieRepository.findAll();
    }

    List<Movie> getMoviesByCount(long count) throws NotFoundException {
        if (count < 0) {
            throw new NotFoundException("Invalid input");
        }

        return movieRepository.getMoviesByCount(count);
    }

    Movie getMovieById(Long id) throws NotFoundException {
        return movieRepository.findById(id).orElseThrow(() -> new NotFoundException("No Movie found"));
    }

    Messages performOperations(Long id, MovieOperations operations) throws NotFoundException {
        if (operations.getType().equals("CHECKOUT")) {
            return checkOutMovie(id);
        }

        if (operations.getType().equals("RETURN")) {
            return returnMovie(id);
        }

        return new Messages();
    }

    private Messages returnMovie(Long movieId) throws NotFoundException {
        Long lastMovieOperationId = movieOperationsRepository.getLastOperationId(movieId);
        // No operation performed on Movie yet
        if (lastMovieOperationId == null) {
            performReturn(movieId);
        }

        MovieOperations lastMovieOperation = movieOperationsRepository.findById(lastMovieOperationId).get();
        // Movie not checked out
        if (lastMovieOperation.getType().equals("AVAILABLE")) {
            Messages message = new Messages();
            message.setMessage(MOVIE_RETURN_FAIL);
            return message;
        }

        if (lastMovieOperation.isDifferentUser()) {
            Messages message = new Messages();
            message.setMessage(MOVIE_RETURN_FAIL_FOR_INVALID_USER);
            return message;
        }

        // Movie can be returned
        return performReturn(movieId);
    }

    private Messages performReturn(Long movieId) throws NotFoundException {
        Movie movie = getMovieById(movieId);
        MovieOperations currentMovieOperation = new MovieOperations();
        movie.returnMovie(currentMovieOperation);
        movieRepository.save(movie);
        Messages message = new Messages();
        message.setMessage(MOVIE_RETURN_SUCCESS);
        return message;
    }

    private Messages checkOutMovie(Long movieId) throws NotFoundException {
        Long lastMovieOperationId = movieOperationsRepository.getLastOperationId(movieId);
        // No operation performed on Movie yet
        if (lastMovieOperationId == null) {
            return performCheckout(movieId);
        }

        // Movie already checked out
        MovieOperations lastMovieOperation = movieOperationsRepository.findById(lastMovieOperationId).get();
        if (lastMovieOperation.getType().equals("CHECKOUT")) {
            Messages message = new Messages();
            message.setMessage(MOVIE_CHECKOUT_FAIL);
            return message;
        }

        // Movie can be checked out
        return performCheckout(movieId);
    }

    private Messages performCheckout(Long movieId) throws NotFoundException {
        Movie movie = getMovieById(movieId);
        MovieOperations currentMovieOperation = new MovieOperations();
        movie.checkOut(currentMovieOperation);
        movieRepository.save(movie);
        Messages message = new Messages();
        message.setMessage(MOVIE_CHECKOUT_SUCCESS);
        return message;
    }
}
