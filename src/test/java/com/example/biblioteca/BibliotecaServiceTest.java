package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BibliotecaServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BibliotecaService bibliotecaService;

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
        Book fetchedBook = bibliotecaService.getBookById(savedBook.getId());
        assertEquals(savedBook, fetchedBook);
    }

    @Test
    void expectNoBookFoundForANonExistentBookId() {
        long nonExistentBookId = 200L;
        assertThrows(NoBooksFoundException.class, () -> bibliotecaService.getBookById(nonExistentBookId));
    }

    @Test
    void shouldListDefaultNumOfBooks() {
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

        List<Book> bookList = bibliotecaService.getBooksByCount((long) defaultNumberOfBooks);

        assertEquals(defaultNumberOfBooks, bookList.size());
    }

    @Test
    void expectEmptyArrayWhenBooksNotAvailableForListing() {
        bookRepository.deleteAll();
        List<Book> bookList = new ArrayList<>();
        assertEquals(0, bibliotecaService.getBooksByCount(2L).size());
    }

    @Test
    void shouldGetBooksOfGivenCount() throws NoBooksFoundException {
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

        List<Book> bookList = bibliotecaService.getBooksByCount(1);

        assertEquals(1, bookList.size());
    }
}