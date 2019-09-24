package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookService {
    private static final String CHECKOUT_SUCCESS = "Thank you! Enjoy the book.";
    private static final String CHECKOUT_FAIL = "That book is not available.";
    private static final String RETURN_SUCCESS = "Thank you for returning the book.";
    private static final String RETURN_FAIL = "That is not a valid book to return.";
    static final String RETURN_FAIL_FOR_INVALID_USER = "Invalid User";

    @SuppressWarnings("unused")
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookOperationsRepository bookOperationsRepository;

    Book getBookById(Long id) throws NotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("No book found"));
    }

    private List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    List<Book> getBooksByCount(long count) throws NotFoundException {
        if (count < 0) {
            throw new NotFoundException("Invalid input");
        }

        return bookRepository.getBooksByCount(count);
    }

    Messages performOperations(Long id, BookOperations operations) throws NotFoundException {
        Messages message = new Messages();

        if (operations.getType().equals("CHECKOUT")) {
            message = (checkOutBook(id));
        }

        if (operations.getType().equals("RETURN")) {
            message = (returnBook(id));
        }

        return message;
    }

    private Messages returnBook(Long bookId) throws NotFoundException {
        Long lastOperationId = bookOperationsRepository.getLastOperationId(bookId);
        // No operation performed on Movie yet
        if (lastOperationId == null) {
            performReturn(bookId);
        }

        BookOperations lastOperation = bookOperationsRepository.findById(lastOperationId).get();
        // Movie not checked out
        if (lastOperation.getType().equals("AVAILABLE")) {
            Messages message = new Messages();
            message.setMessage(RETURN_FAIL);
            return message;
        }

        if (lastOperation.isDifferentUser()) {
            Messages message = new Messages();
            message.setMessage(RETURN_FAIL_FOR_INVALID_USER);
            return message;
        }

        // Movie can be returned
        return performReturn(bookId);
    }

    private Messages performReturn(Long bookId) throws NotFoundException {
        Book book = getBookById(bookId);
        BookOperations bookOperations = new BookOperations();
        book.returnBook(bookOperations);
        bookRepository.save(book);
        Messages message = new Messages();
        message.setMessage(RETURN_SUCCESS);
        return message;
    }

    private Messages checkOutBook(Long bookId) throws NotFoundException {
        Long lastOperationId = bookOperationsRepository.getLastOperationId(bookId);
        // No operation performed on Movie yet
        if (lastOperationId == null) {
            return performCheckout(bookId);
        }

        // Movie already checked out
        BookOperations lastOperation = bookOperationsRepository.findById(lastOperationId).get();
        if (lastOperation.getType().equals("CHECKOUT")) {
            Messages message = new Messages();
            message.setMessage(CHECKOUT_FAIL);
            return message;
        }

        // Movie can be checked out
        return performCheckout(bookId);
    }

    private Messages performCheckout(Long bookId) throws NotFoundException {
        Book book = getBookById(bookId);
        BookOperations currentMovieOperation = new BookOperations();
        book.checkOut(currentMovieOperation);
        bookRepository.save(book);
        Messages message = new Messages();
        message.setMessage(CHECKOUT_SUCCESS);
        return message;
    }
}
