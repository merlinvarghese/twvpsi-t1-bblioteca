package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unused")
@SpringBootTest
class BibliotecaServiceTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    void expectBookDetailsForAGivenBookId() throws Exception {
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA",
                "AVAILABLE");

        Book savedBook = bookRepository.save(book);
        Book fetchedBook = bibliotecaService.getBookById(savedBook.getId());

        assertEquals(savedBook, fetchedBook);
    }

    @Test
    void expectNoBookFoundForANonExistentBookId() {
        long nonExistentBookId = 200L;

        assertThrows(NotFoundException.class, () -> bibliotecaService.getBookById(nonExistentBookId));
    }

    @Test
    void shouldGetBooksOfGivenCount() throws NotFoundException {
        bookRepository.deleteAll();
        Book book1 = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA",
                "AVAILABLE");
        Book book2 = new Book((long) 2,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA",
                "AVAILABLE");
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> bookList = bibliotecaService.getBooksByCount(1);

        assertEquals(1, bookList.size());
    }

    @Test
    void expectDefaultNumberOfMoviesList() throws NotFoundException {
        int defaultNumberOfMovies = 1;
        movieRepository.deleteAll();
        Movie movie1 = new Movie(  1,
                "Harry potter",
                "2003",
                "Chris Columbus", "8");
        Movie movie2 = new Movie(2,
                "Finding Nemo",
                "2003",
                "Andrew Stanton", "7");
        movieRepository.save(movie1);
        movieRepository.save(movie2);

        List<Movie> movieList = bibliotecaService.getMoviesByCount(defaultNumberOfMovies);

        assertEquals(defaultNumberOfMovies, movieList.size());
    }

    @Test
    void expectEmptyArrayWhenMoviesNotAvailableForListing() throws NotFoundException {
        movieRepository.deleteAll();
        assertEquals(0, bibliotecaService.getMoviesByCount(2L).size());
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

        List<Movie> movieCount = bibliotecaService.getMoviesByCount(1);

        assertEquals(1, movieCount.size());
    }

    @Test
    void expectListDefaultNumOfBooks() throws NotFoundException {
        int defaultNumberOfBooks = 1;
        bookRepository.deleteAll();
        Book book1 = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA",
                "AVAILABLE");
        Book book2 = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA",
                "AVAILABLE");
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> bookList = bibliotecaService.getBooksByCount(defaultNumberOfBooks);

        assertEquals(defaultNumberOfBooks, bookList.size());
    }

    @Test
    void expectEmptyArrayWhenBooksNotAvailableForListing() throws NotFoundException {
        bookRepository.deleteAll();
        assertEquals(0, bibliotecaService.getBooksByCount(2L).size());
    }

    @Test
    void expectListOfBooksByCount() throws NotFoundException {
        bookRepository.deleteAll();
        Book book1 = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA",
                "AVAILABLE");
        Book book2 = new Book((long) 2,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA",
                "AVAILABLE");
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> bookList = bibliotecaService.getBooksByCount(1);

        assertEquals(1, bookList.size());
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
        Movie fetchedMovie = bibliotecaService.getMovieById(savedMovie.getId());
        assertEquals(savedMovie, fetchedMovie);
    }

    @Test
    void expectNoMovieFoundForANonExistentMovieId() {
        long nonExistentMovieId = 200L;
        assertThrows(NotFoundException.class, () -> bibliotecaService.getBookById(nonExistentMovieId));
    }

    @Test
    void expectSuccessfulBookCheckout() throws NotFoundException {
        String expectedMessage = "Thank you! Enjoy the book.";
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA","AVAILABLE");
        Book savedBook = bookRepository.save(book);
        Messages message = bibliotecaService.checkOutBook(savedBook.getId());

        assertEquals(expectedMessage, message.getMessage());
    }

    @Test
    void expectFailsToCheckoutWhenBookNotAvailable() throws NotFoundException {
        String expectedMessage = "That book is not available.";
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA","CHECKEDOUT");
        Book savedBook = bookRepository.save(book);
        Messages message = bibliotecaService.checkOutBook(savedBook.getId());

        assertEquals(expectedMessage, message.getMessage());
    }

    @Test
    void expectSuccessfulBookCheckIn() throws NotFoundException {
        String expectedMessage = "Thank you for returning the book.";
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA","CHECKEDOUT");
        Book savedBook = bookRepository.save(book);
        Messages message = bibliotecaService.returnBook(savedBook.getId());

        assertEquals(expectedMessage, message.getMessage());
    }

    @Test
    void expectFailsToCheckInWhenBookNotCheckedOut() throws NotFoundException {
        String expectedMessage = "That is not a valid book to return.";
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA","AVAILABLE");
        Book savedBook = bookRepository.save(book);
        Messages message = bibliotecaService.returnBook(savedBook.getId());

        assertEquals(expectedMessage, message.getMessage());
    }
    @Test
    void expectSuccessfulMovieCheckout() throws NotFoundException {
        String expectedMessage = "Thank you! Enjoy the book.";
        movieRepository.deleteAll();
        Movie movie = new Movie((long) 1,
                "Harry potter",
                "2003",
                "Chris Columbus", "8");
        Movie savedMovie = movieRepository.save(movie);
        Messages message = bibliotecaService.checkOutBook(savedMovie.getId());

        assertEquals(expectedMessage, message.getMessage());
    }

    @Test
    void expectFailsToCheckoutWhenMovieNotAvailable() throws NotFoundException {
        String expectedMessage = "That book is not available.";
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA","CHECKEDOUT");
        Book savedBook = bookRepository.save(book);
        Messages message = bibliotecaService.checkOutBook(savedBook.getId());

        assertEquals(expectedMessage, message.getMessage());
    }

    @Test
    void expectSuccessfulMovieCheckIn() throws NotFoundException {
        String expectedMessage = "Thank you for returning the book.";
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA","CHECKEDOUT");
        Book savedBook = bookRepository.save(book);
        Messages message = bibliotecaService.returnBook(savedBook.getId());

        assertEquals(expectedMessage, message.getMessage());
    }

    @Test
    void expectFailsToCheckInWhenMovieNotCheckedOut() throws NotFoundException {
        String expectedMessage = "That is not a valid book to return.";
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA","AVAILABLE");
        Book savedBook = bookRepository.save(book);
        Messages message = bibliotecaService.returnBook(savedBook.getId());

        assertEquals(expectedMessage, message.getMessage());
    }
}
