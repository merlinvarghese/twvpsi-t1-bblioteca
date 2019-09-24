package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    @Test
    void expectSuccessfulCheckout() {
        Movie movie = new Movie((long) 1,
                "Harry potter",
                "2003",
                "Chris Columbus",
                "8");
        MovieOperations checkoutOp = new MovieOperations("CHECKOUT");

        movie.checkOut(checkoutOp);

        assertEquals(1, movie.getOperations().size());
        assertEquals("CHECKOUT", checkoutOp.getType());
    }

    @Test
    void expectSuccessfulReturn() {
        Movie movie = new Movie((long) 1,
                "Harry potter",
                "2003",
                "Chris Columbus",
                "8");
        MovieOperations returnOp = new MovieOperations("RETURN");

        movie.returnMovie(returnOp);

        assertEquals(1, movie.getOperations().size());
        assertEquals("AVAILABLE", returnOp.getType());
    }

}