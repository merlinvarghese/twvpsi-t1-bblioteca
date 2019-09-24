package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.example.biblioteca.MovieService.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class MovieServiceTest {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieOperationsRepository movieOperationsRepository;

    @Autowired
    private MovieService movieService;

    @Test
    void expectDefaultNumberOfMoviesList() throws NotFoundException {
        int defaultNumberOfMovies = 1;
        movieRepository.deleteAll();
        Movie movie1 = new Movie(1,
                "Harry potter",
                "2003",
                "Chris Columbus", "8");
        Movie movie2 = new Movie(2,
                "Finding Nemo",
                "2003",
                "Andrew Stanton", "7");
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        List<Movie> movieList = movieService.getMoviesByCount(defaultNumberOfMovies);

        assertEquals(defaultNumberOfMovies, movieList.size());
    }

    @Test
    void expectEmptyArrayWhenMoviesNotAvailableForListing() throws NotFoundException {
        movieRepository.deleteAll();
        assertEquals(0, movieService.getMoviesByCount(2L).size());
    }

    @Test
    void expectListOfMoviesByCount() throws NotFoundException {
        movieRepository.deleteAll();
        Movie movie1 = new Movie((long) 1,
                "Harry potter",
                "2003",
                "Chris Columbus", "8");
        Movie movie2 = new Movie((long) 2,
                "Finding Nemo",
                "2003",
                "Andrew Stanton", "7");

        movieRepository.save(movie1);
        movieRepository.save(movie2);

        List<Movie> movieCount = movieService.getMoviesByCount(1);

        assertEquals(1, movieCount.size());
    }

    @Test
    void expectMovieDetailsForAGivenMovieId() throws Exception {
        movieRepository.deleteAll();
        Movie movie = new Movie((long) 1,
                "Harry potter",
                "2003",
                "Chris Columbus",
                "8");
        Movie savedMovie = movieRepository.save(movie);
        Movie fetchedMovie = movieService.getMovieById(savedMovie.getId());
        assertEquals(savedMovie, fetchedMovie);
    }

    @Test
    void expectNoMovieFoundForANonExistentMovieId() {
        long nonExistentMovieId = 200L;
        assertThrows(NotFoundException.class, () -> movieService.getMovieById(nonExistentMovieId));
    }

    @Test
    void expectSuccessfulCheckoutOfMovie() throws NotFoundException {
        movieRepository.deleteAll();
        movieOperationsRepository.deleteAll();
        Movie movie = new Movie((long) 1,
                "Harry potter",
                "2003",
                "Chris Columbus",
                "8");
        Movie savedMovie = movieRepository.save(movie);
        MovieOperations movieOperation = new MovieOperations("CHECKOUT");

        Messages outputMsg = movieService.performOperations(savedMovie.getId(), movieOperation);

        assertEquals(MOVIE_CHECKOUT_SUCCESS, outputMsg.getMessage());
    }

    @Test
    void expectFailToCheckoutOfMovieWhichIsAlreadyCheckedOut() throws NotFoundException {
        movieRepository.deleteAll();
        movieOperationsRepository.deleteAll();
        Movie movie = new Movie((long) 1,
                "Harry potter",
                "2003",
                "Chris Columbus",
                "8");
        Movie savedMovie = movieRepository.save(movie);
        MovieOperations movieOperation = new MovieOperations("CHECKOUT");
        movieService.performOperations(savedMovie.getId(), movieOperation);

        Messages outputMsg = movieService.performOperations(savedMovie.getId(), movieOperation);

        assertEquals(MOVIE_CHECKOUT_FAIL, outputMsg.getMessage());
    }

    @Test
    void expectSuccessfulReturnOfMovie() throws NotFoundException {
        movieRepository.deleteAll();
        movieOperationsRepository.deleteAll();
        Movie movie = new Movie((long) 1,
                "Harry potter",
                "2003",
                "Chris Columbus",
                "8");
        Movie savedMovie = movieRepository.save(movie);
        MovieOperations checkoutOp = new MovieOperations("CHECKOUT");
        movieService.performOperations(savedMovie.getId(), checkoutOp);
        MovieOperations returnOp = new MovieOperations("RETURN");

        Messages outputMsg = movieService.performOperations(savedMovie.getId(), returnOp);

        assertEquals(MOVIE_RETURN_SUCCESS, outputMsg.getMessage());
    }

    @Test
    void expectReturnFailsOfAnAvailableMovie() throws NotFoundException {
        movieRepository.deleteAll();
        movieOperationsRepository.deleteAll();
        Movie movie = new Movie((long) 3,
                "Harry potter",
                "2003",
                "Chris Columbus",
                "8");
        Movie savedMovie = movieRepository.save(movie);
        MovieOperations returnOp = new MovieOperations("RETURN");

        Messages outputMsg = movieService.performOperations(savedMovie.getId(), returnOp);

        assertEquals(MOVIE_RETURN_FAIL, outputMsg.getMessage());
    }
}
