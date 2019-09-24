package com.example.biblioteca;

import org.junit.jupiter.api.Test;

class BookTest {
    @Test
    void expectSuccessfulCheckoutForAvailableBook() {
        Book book = new Book(1L, "786863986", "A Monk Swimming", "Malachy McCourt",
                "1998", "Hyperion", "AVAILABLE");

        assertTrue(book.checkOut(currentBookOperation));
        assertEquals("CHECKEDOUT", book.getCheckout_status());
    }

    @Test
    void expectFailsToCheckoutForUnavailableBook() {
        Book book = new Book(1L, "786863986", "A Monk Swimming", "Malachy McCourt",
                "1998", "Hyperion", "CHECKEDOUT");

        assertFalse(book.checkOut(currentBookOperation));
    }

    @Test
    void expectSuccessfulCheckInForCheckedOutBook() {
        Book book = new Book(1L, "786863986", "A Monk Swimming", "Malachy McCourt",
                "1998", "Hyperion", "CHECKEDOUT");

        assertTrue(book.checkIn());
        assertEquals("AVAILABLE", book.getCheckout_status());
    }

    @Test
    void expectFailsToCheckInForAvailableBook() {
        Book book = new Book(1L, "786863986", "A Monk Swimming", "Malachy McCourt",
                "1998", "Hyperion", "AVAILABLE");

        assertFalse(book.checkIn());
    }
}
