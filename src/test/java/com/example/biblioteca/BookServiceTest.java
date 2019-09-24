package com.example.biblioteca;

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
                "Vintage Books USA",
                "AVAILABLE");

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

        List<Book> bookList = bookService.getBooksByCount(1);

        assertEquals(1, bookList.size());
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
        Messages message = bookService.checkout(savedBook.getId());

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
        Messages message = bookService.checkout(savedBook.getId());

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
        Messages message = bookService.returnBook(savedBook.getId());

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
        Messages message = bookService.returnBook(savedBook.getId());

        assertEquals(expectedMessage, message.getMessage());
    }
}
