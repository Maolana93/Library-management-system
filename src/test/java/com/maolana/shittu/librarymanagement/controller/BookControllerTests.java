package com.maolana.shittu.librarymanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.maolana.shittu.librarymanagement.exception.BookNotFoundException;
import com.maolana.shittu.librarymanagement.model.Book;
import com.maolana.shittu.librarymanagement.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenBookObject_whenCreateBook_thenReturnSavedBook() throws Exception {

        // Given
        Book book = Book.builder()
            .title("Book1")
            .isbn("ISBN 1")
            .author("HassanC")
            .build();

        given(bookService.addBook(any(Book.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));

        // When
        ResultActions response = mockMvc.perform(post("/api/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(book)));

        // Then
        response.andDo(print()).
            andExpect(status().isCreated())
            .andExpect(jsonPath("$.title",
                is(book.getTitle())))
            .andExpect(jsonPath("$.isbn",
                is(book.getIsbn())))
            .andExpect(jsonPath("$.author",
                is(book.getAuthor())));

    }

    @Test
    public void givenListOfBooks_whenGetAllBooks_thenReturnBooksList() throws Exception {
        // Given
        List<Book> listOfBooks = new ArrayList<>();
        listOfBooks.add(Book.builder().title("Book1").isbn("ISBN 1").author("Hassan").build());
        listOfBooks.add(Book.builder().title("Book2").isbn("ISBN 2").author("HassanC").build());
        given(bookService.getAllBooks()).willReturn(listOfBooks);

        // When
        ResultActions response = mockMvc.perform(get("/api/books"));

        // Then
        response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.size()",
                is(listOfBooks.size())));

    }

    @Test
    public void givenBookId_whenGetBookById_thenReturnBookObject() throws Exception {
        // Given
        long bookId = 1L;
        Book book = Book.builder()
            .title("Book1")
            .isbn("ISBN 1")
            .author("Hassan")
            .build();

        given(bookService.getBookById(bookId)).willReturn(book);

        // When
        ResultActions response = mockMvc.perform(get("/api/books/{id}", bookId));

        // Then
        response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.title", is(book.getTitle())))
            .andExpect(jsonPath("$.isbn", is(book.getIsbn())))
            .andExpect(jsonPath("$.author", is(book.getAuthor())));

    }

    @Test
    public void givenInvalidBookId_whenGetBookById_thenReturnEmpty() throws Exception {
        // Given
        long bookId = 1L;

        given(bookService.getBookById(bookId)).willThrow(BookNotFoundException.class);

        // When
        ResultActions response = mockMvc.perform(get("/api/books/{id}", bookId));

        // Then
        response.andExpect(status().isNotFound())
            .andDo(print());

    }

    @Test
    public void givenUpdatedBook_whenUpdateBook_thenReturnUpdateBookObject() throws Exception {
        // Given
        long bookId = 1L;
        Book savedBook = Book.builder()
            .title("Book1")
            .isbn("ISBN 1")
            .author("hassanC")
            .build();

        Book updatedBook = Book.builder()
            .title("Book1")
            .isbn("ISBN 1")
            .author("hassanC")
            .build();
        given(bookService.getBookById(bookId)).willReturn(savedBook);
        given(bookService.updateBook(anyLong(), any(Book.class)))
            .willAnswer((invocation) -> invocation.getArgument(1));

        // When
        ResultActions response = mockMvc.perform(put("/api/books/{id}", bookId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedBook)));

        // Then
        response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.title", is(updatedBook.getTitle())))
            .andExpect(jsonPath("$.isbn", is(updatedBook.getIsbn())))
            .andExpect(jsonPath("$.author", is(updatedBook.getAuthor())));
    }

    @Test
    public void givenUpdatedBook_whenUpdateBook_thenReturn404() throws Exception {
        // Given
        long bookId = 1L;

        Book updatedBook = Book.builder()
            .title("Book1")
            .isbn("ISBN 1")
            .author("HassanC")
            .build();

        given(bookService.updateBook(anyLong(), any(Book.class))).willThrow(BookNotFoundException.class);

        // When
        ResultActions response = mockMvc.perform(put("/api/books/{id}", bookId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedBook)));

        // Then
        response.andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    public void givenBookId_whenDeleteBook_thenReturn200() throws Exception {
        // Given
        long bookId = 1L;
        willDoNothing().given(bookService).deleteBook(bookId);

        // When
        ResultActions response = mockMvc.perform(delete("/api/books/{id}", bookId));

        // Then
        response.andExpect(status().isOk())
            .andDo(print());
    }
}
