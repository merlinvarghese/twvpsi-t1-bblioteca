package com.example.biblioteca;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Ignore
class BookTest {
    @Test
    void expectSuccessfulCheckoutForAvailableBook() {
        Book book = new Book(1L, "786863986", "A Monk Swimming", "Malachy McCourt",
                "1998", "Hyperion");

        //verify(book.checkOut(););
    }

    @Test
    void expectFailsToCheckoutForUnavailableBook() {
        Book book = new Book(1L, "786863986", "A Monk Swimming", "Malachy McCourt",
                "1998", "Hyperion");

        //verify(book.checkOut());
    }

    @Test
    void expectSuccessfulCheckInForCheckedOutBook() {
        Book book = new Book(1L, "786863986", "A Monk Swimming", "Malachy McCourt",
                "1998", "Hyperion");

        //assertTrue(book.checkIn());
    }

    @Test
    void expectFailsToCheckInForAvailableBook() {
        Book book = new Book(1L, "786863986", "A Monk Swimming", "Malachy McCourt",
                "1998", "Hyperion");

        //assertFalse(book.checkIn());
    }
}
