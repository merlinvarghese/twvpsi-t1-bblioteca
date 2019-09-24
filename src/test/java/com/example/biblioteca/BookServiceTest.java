package com.example.biblioteca;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("unused")
@SpringBootTest
class BookServiceTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

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
                "Vintage Books USA");

        Book savedBook = bookRepository.save(book);
        Book fetchedBook = bookService.getBookById(savedBook.getId());

        assertEquals(savedBook, fetchedBook);
    }

    @Test
    void expectNoBookFoundForANonExistentBookId() {
        long nonExistentBookId = 200L;

        assertThrows(NotFoundException.class, () -> bookService.getBookById(nonExistentBookId));
    }

    @Test
    void shouldGetBooksOfGivenCount() throws NotFoundException {
        bookRepository.deleteAll();
        Book book1 = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        Book book2 = new Book((long) 2,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> bookList = bookService.getBooksByCount(1);

        assertEquals(1, bookList.size());
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
                "Vintage Books USA");
        Book book2 = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> bookList = bookService.getBooksByCount(defaultNumberOfBooks);

        assertEquals(defaultNumberOfBooks, bookList.size());
    }

    @Test
    void expectEmptyArrayWhenBooksNotAvailableForListing() throws NotFoundException {
        bookRepository.deleteAll();
        assertEquals(0, bookService.getBooksByCount(2L).size());
    }

    @Test
    void expectListOfBooksByCount() throws NotFoundException {
        bookRepository.deleteAll();
        Book book1 = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        Book book2 = new Book((long) 2,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> bookList = bookService.getBooksByCount(1);

        assertEquals(1, bookList.size());
    }



    @Test
    void expectSuccessfulBookCheckout() throws NotFoundException {
        String expectedMessage = "Thank you! Enjoy the book.";
        BookOperations operations = new BookOperations();
        operations.setType("CHECKOUT");
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        Book savedBook = bookRepository.save(book);
        Messages message = bookService.performOperations(savedBook.getId(), operations);

        assertEquals(expectedMessage, message.getMessage());
    }

    @Ignore
    void expectFailsToCheckoutWhenBookNotAvailable() throws NotFoundException {
        String expectedMessage = "That book is not available.";
        BookOperations operations = new BookOperations();
        operations.setType("CHECKOUT");
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        Book savedBook = bookRepository.save(book);
        Messages message = bookService.performOperations(savedBook.getId(), operations);
        Messages messages2 = bookService.performOperations(savedBook.getId(),operations);

        assertEquals(expectedMessage, messages2.getMessage());
    }

    @Ignore
    void expectSuccessfulBookCheckIn() throws NotFoundException {
        String expectedMessage = "Thank you for returning the book.";
        BookOperations operations = new BookOperations();
        operations.setType("RETURN");
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        Book savedBook = bookRepository.save(book);
        Messages message = bookService.performOperations(savedBook.getId(), operations);

        assertEquals(expectedMessage, message.getMessage());
    }

    @Ignore
    void expectFailsToCheckInWhenBookNotCheckedOut() throws NotFoundException {
        String expectedMessage = "That is not a valid book to return.";
        BookOperations operations = new BookOperations();
        operations.setType("RETURN");
        bookRepository.deleteAll();
        Book book = new Book((long) 1,
                "375704965",
                "Harry Potter",
                "JK Rowling",
                "1990",
                "Vintage Books USA");
        Book savedBook = bookRepository.save(book);
        Messages message = bookService.performOperations(savedBook.getId(), operations);

        assertEquals(expectedMessage, message.getMessage());
    }
}
