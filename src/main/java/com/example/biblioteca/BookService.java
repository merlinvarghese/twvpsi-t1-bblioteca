package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookService {
    private static final String CHECKOUT_SUCCESS = "Thank you! Enjoy the movie.";
    private static final String CHECKOUT_FAIL = "That movie is not available.";
    private static final String RETURN_SUCCESS = "Thank you for returning the movie.";
    private static final String RETURN_FAIL = "That is not a valid movie to return.";
    private static final String RETURN_FAIL_FOR_INVALID_USER = "This is not valid user to return this book";
    ​
    @SuppressWarnings("unused")
    @Autowired
    private BookRepository bookRepository;
​
    @SuppressWarnings("unused")
    @Autowired
    private BookOperationsRepository bookOperationsRepository;
​
    private List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }
​
    List<Movie> getBooksByCount(long count) throws NotFoundException {
        if (count < 0) {
            throw new NotFoundException("Invalid input");
        }
​
        return bookRepository.getBooksByCount(count);
    }
​
    Book getBookById(Long id) throws NotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("No Book found"));
    }
​
    Messages performOperations(Long id, BookOperations operations) throws NotFoundException {
        if (operations.getStatus().equals("CHECKOUT")) {
            return checkOutMovie(id);
        }
​
        if (operations.getStatus().equals("RETURN")) {
            return returnMovie(id);
        }
​
        return new Messages();
    }
​
    private Messages returnMovie(Long bookId) throws NotFoundException {
        Long lastMovieOperationId = bookOperationsRepository.getLastBookOperationId(bookId);
        // No operation performed on Movie yet
        if (lastMovieOperationId == null) {
            performReturn(bookId);
        }
​
        BookOperations lastMovieOperation = bookOperationsRepository.findById(lastMovieOperationId).get();
        // Movie not checked out
        if (lastMovieOperation.getStatus().equals("AVAILABLE")) {
            Messages message = new Messages();
            message.setMessage(RETURN_FAIL);
            return message;
        }
​
        if (lastMovieOperation.isDifferentUser()) {
            Messages message = new Messages();
            message.setMessage(RETURN_FAIL_FOR_INVALID_USER);
            return message;
        }
​
        // Movie can be returned
        return performReturn(bookId);
    }
​
    private Messages performReturn(Long bookId) throws NotFoundException {
        Book book = getBookById(bookId);
        BookOperations currentMovieOperation = new BookOperations();
        book.returnMovie(currentMovieOperation);
        bookRepository.save(book);
        Messages message = new Messages();
        message.setMessage(RETURN_SUCCESS);
        return message;
    }
​
    private Messages checkOutMovie(Long bookId) throws NotFoundException {
        Long lastBookOperationId = bookOperationsRepository.getLastBookOperationId(bookId);
        // No operation performed on Movie yet
        if (lastBookOperationId == null) {
            return performCheckout(bookId);
        }
​
        // Movie already checked out
        BookOperations lastMovieOperation = bookOperationsRepository.findById(lastBookOperationId).get();
        if (lastMovieOperation.getStatus().equals("CHECKOUT")) {
            Messages message = new Messages();
            message.setMessage(CHECKOUT_FAIL);
            return message;
        }
​
        // Movie can be checked out
        return performCheckout(bookId);
    }
​
    private Messages performCheckout(Long movieId) throws NotFoundException {
        Book book = getBookById(movieId);
        BookOperations currentBookOperation = new BookOperations();
        book.checkOut(currentBookOperation);
        bookRepository.save(book);
        Messages message = new Messages();
        message.setMessage(CHECKOUT_SUCCESS);
        return message;
    }

}
