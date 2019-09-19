package com.example.biblioteca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookTest {
    @Test
    void expectSuccessCheckout()
    {
        Book book = new Book(1L,"786863986","A Monk Swimming","Malachy McCourt",
                "1998","Hyperion","AVAILABLE");
        Book book1 = new Book(1L,"786863986","A Monk Swimming","Malachy McCourt",
                "1998","Hyperion","AVAILABLE");
        book1.setCheckout_status("CHECKEDOUT");
        assertTrue(book1.checkout(book));
    }
}
