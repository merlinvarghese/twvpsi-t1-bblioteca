package com.example.biblioteca;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@TestPropertySource(properties = {"default.books.count = 1", "default.movies.count = 1"})
class BibliotecaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BibliotecaService bibliotecaService;

    @Test
    void expectWelcomeMessageOnBibliotecaInvocation() throws Exception {
        mockMvc.perform(get(""))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to Biblioteca!"));
    }

    @Test
    void expectBookDetailsForGivenBookId() throws Exception {
        when(bibliotecaService.getBookById(1L)).thenReturn(
                (new Book(1L, "375704965", "A Judgement in Stone",
                        "Ruth Rendell", "2000", "Vintage Books USA",
                        "AVAILABLE")));

        mockMvc.perform(get("/books/{id}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"isbn\":\"375704965\",\"title\":\"A Judgement in Stone\"" +
                        ", \"author\":\"Ruth Rendell\",\"published_year\":\"2000\", \"publisher\":\"Vintage Books USA\"}"));

        verify(bibliotecaService).getBookById(1L);
    }

    @Test
    void expectNoBookReturnedWhenBookWithGivenIdDoesNotExist() throws Exception {
        when(bibliotecaService.getBookById(200L)).thenThrow(new NotFoundException("No Book found"));

        mockMvc.perform(get("/books/{id}", 200)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bibliotecaService).getBookById(200L);
    }

    @Test
    void expectEmptyListWhenNoBooksAreAvailable() throws Exception {
        long defaultNumberOfBooks = 2L;
        List<Book> bookList = new ArrayList<>();
        when(bibliotecaService.getBooksByCount(defaultNumberOfBooks)).thenReturn(bookList);

        mockMvc.perform(get("/books?max=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(bibliotecaService).getBooksByCount(defaultNumberOfBooks);
    }

    @Test
    void expectListOfBooksByCount() throws Exception {
        List<Book> books = Collections.singletonList(
                new Book((long) 1,
                        "375704965",
                        "Harry Potter",
                        "JK Rowling",
                        "1990",
                        "Vintage Books USA",
                        "AVAILABLE"
                        )
        );
        when(bibliotecaService.getBooksByCount(1)).thenReturn(books);

        mockMvc.perform(get("/books?max=1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"isbn\":\"375704965\"," +
                        "\"title\":\"Harry Potter\"," +
                        "\"author\":\"JK Rowling\"," +
                        "\"published_year\":\"1990\"," +
                        "\"publisher\":\"Vintage Books USA\"}]"));

        verify(bibliotecaService).getBooksByCount(1);
    }

    @Test
    void expectDefaultNumberOfBooksWhenMaxNotSpecified() throws Exception {
        List<Book> books = Collections.singletonList(
                new Book((long) 1,
                        "375704965",
                        "Harry Potter",
                        "JK Rowling",
                        "1990",
                        "Vintage Books USA",
                        "AVAILABLE")
        );
        when(bibliotecaService.getBooksByCount(1)).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(bibliotecaService).getBooksByCount(1);
    }

    @Test
    void expectFailsToListBooksWhenBooksListCountIsNegative() throws Exception {
        when(bibliotecaService.getBooksByCount(-1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/books?max=-1"))
                .andExpect(status().isNotFound());

        verify(bibliotecaService).getBooksByCount(-1);
    }

    @Test
    void expectEmptyListWhenNoMoviesAreAvailable() throws Exception {
        long defaultNumberOfMovies = 2L;
        List<Movie> movieList = new ArrayList<>();
        when(bibliotecaService.getMoviesByCount(defaultNumberOfMovies)).thenReturn(movieList);

        mockMvc.perform(get("/movies?max=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(bibliotecaService).getMoviesByCount(defaultNumberOfMovies);
    }

    @Test
    void expectListOfMoviesByCount() throws Exception {
        List<Movie> movies = Collections.singletonList(
                new Movie(1L,
                        "Harry potter",
                        "2003",
                        "Chris Columbus", "8"));
        when(bibliotecaService.getMoviesByCount(1)).thenReturn(movies);

        mockMvc.perform(get("/movies?max=1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"Harry potter\"," +
                        "\"year\":\"2003\"," +
                        "\"director\":\"Chris Columbus\"," +
                        "\"rating\":\"8\"}]"));

        verify(bibliotecaService).getMoviesByCount(1);
    }

    @Test
    void expectDefaultNumberOfMoviesWhenMaxNotSpecified() throws Exception {
        List<Movie> movies = Collections.singletonList(
                new Movie(1L,
                        "Harry potter",
                        "2003",
                        "Chris Columbus", "8"));
        when(bibliotecaService.getMoviesByCount(1)).thenReturn(movies);

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(bibliotecaService).getMoviesByCount(1);
    }

    @Test
    void expectFailsWhenListCountIsNegativeForMovies() throws Exception {
        when(bibliotecaService.getMoviesByCount(-1)).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/movies?max=-1"))
                .andExpect(status().isNotFound());

        verify(bibliotecaService).getMoviesByCount(-1);
    }

    @Test
    void expectMovieDetailsForAGivenMovieId() throws Exception {
        when(bibliotecaService.getMovieById(1L)).thenReturn(
                (new Movie(1L, "Harry potter", "2003", "Chris Columbus",
                        "8")));

        mockMvc.perform(get("/movies/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Harry potter\",\"year\":\"2003\",\"director\":\"Chris Columbus\"," +
                        "\"rating\":\"8\"}"
                ))
        ;

        verify(bibliotecaService).getMovieById(1L);
    }

    @Test
    void expectNoMovieFoundForAGivenMovieId() throws Exception {
        when(bibliotecaService.getMovieById(200L)).thenThrow(new NotFoundException("No Movie found"));

        mockMvc.perform(get("/movies/{id}", 200)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bibliotecaService).getMovieById(200L);
    }

    @Test
    void expectSuccessFulBookCheckOut() throws Exception {
        String checkout_message = "Thank you! Enjoy the book.";
        Messages message = new Messages();
        message.setMessage(checkout_message);
        when(bibliotecaService.checkout(any(Long.class))).thenReturn(message);

        mockMvc.perform(patch("/books/1/checkout"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\":\"Thank you! Enjoy the book.\"}"));

        verify(bibliotecaService).checkout(1L);
    }

    @Test
    void expectFailsToCheckoutWhenBookNotAvailable() throws Exception {
        String checkout_message = "That book is not available.";
        Messages message = new Messages();
        message.setMessage(checkout_message);
        when(bibliotecaService.checkout(any(Long.class))).thenReturn(message);

        mockMvc.perform(patch("/books/1/checkout"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\":\"That book is not available.\"}"));

        verify(bibliotecaService).checkout(1L);
    }

    @Test
    void expectSuccessfulBookCheckIn() throws Exception {
        String checkin_message = "Thank you for returning the book.";
        Messages message = new Messages();
        message.setMessage(checkin_message);
        when(bibliotecaService.returnBook(any(Long.class))).thenReturn(message);

        mockMvc.perform(patch("/books/1/checkin"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\":\"Thank you for returning the book.\"}"));

        verify(bibliotecaService).returnBook(1L);
    }

    @Test
    void expectFailsToCheckInWhenBookNotCheckedOut() throws Exception {
        String checkin_message = "That is not a valid book to return.";
        Messages message = new Messages();
        message.setMessage(checkin_message);
        when(bibliotecaService.returnBook(any(Long.class))).thenReturn(message);

        mockMvc.perform(patch("/books/1/checkin"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\":\"That is not a valid book to return.\"}"));

        verify(bibliotecaService).returnBook(1L);
    }

}
