package com.example.biblioteca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
class BookService {
    private static final String CHECKOUT_SUCCESS = "Thank you! Enjoy the book.";
    private static final String CHECKOUT_FAIL = "That book is not available.";
    private static final String RETURN_SUCCESS = "Thank you for returning the book.";
    private static final String RETURN_FAIL = "That is not a valid book to return.";

    @SuppressWarnings("unused")
    @Autowired
    private BookRepository bookRepository;

    Book getBookById(Long id) throws NotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new NotFoundException("No book found"));
    }

    private List<Book> getAllBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();
        return books.stream().filter(book -> book.getCheckout_status().equals("AVAILABLE")).collect(Collectors.toList());
    }

    List<Book> getBooksByCount(long count) throws NotFoundException {
        if ( count <0 ) {
            throw new NotFoundException("Invalid input");
        }
        List<Book> books = getAllBooks();
        List<Book> resultBooks = new ArrayList<>();
        for (int idx = 0; idx < books.size() && idx < count; idx++) {
            resultBooks.add(books.get(idx));
        }
        return resultBooks;
    }

    Messages checkout(Long id) throws NotFoundException {
        Messages message = new Messages();
        Book book = getBookById(id);
        boolean checkoutSuccess = book.checkOut();
        if (checkoutSuccess) {
            bookRepository.save(book);
            message.setMessage(CHECKOUT_SUCCESS);
        } else {
            message.setMessage(CHECKOUT_FAIL);
        }
        return message;
    }

    Messages returnBook(Long id) throws NotFoundException {
        Messages message = new Messages();
        Book book = getBookById(id);
        boolean returnSuccess = book.checkIn();
        if (returnSuccess) {
            bookRepository.save(book);
            message.setMessage(RETURN_SUCCESS);
        } else {
            message.setMessage(RETURN_FAIL);
        }
        return message;
    }
}
