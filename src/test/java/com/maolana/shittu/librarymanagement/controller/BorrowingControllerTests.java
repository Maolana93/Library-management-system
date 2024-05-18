package com.maolana.shittu.librarymanagement.controller;


import com.maolana.shittu.librarymanagement.exception.BookNotFoundException;
import com.maolana.shittu.librarymanagement.exception.BookOutOfStockException;
import com.maolana.shittu.librarymanagement.exception.BorrowNotFoundException;
import com.maolana.shittu.librarymanagement.exception.PatronNotFoundException;
import com.maolana.shittu.librarymanagement.model.Borrow;
import com.maolana.shittu.librarymanagement.service.BorrowingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowingController.class)
@AutoConfigureMockMvc
public class BorrowingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowingService borrowingService;

    @Test
    public void givenBookIdAndPatronId_whenBorrowBook_thenReturn200() throws Exception {

        // Given
        long bookId = 1L;
        long patronId = 1L;

        given(borrowingService.borrowBook(bookId, patronId)).willReturn(new Borrow());


        // When
        ResultActions response = mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId));

        // Then
        response.andDo(print()).
            andExpect(status().isOk());

    }

    @Test
    public void givenBookIdAndPatronId_whenBorrowAndBookDoesNotExists_thenReturn422() throws Exception {
        // Given
        long bookId = 1L;
        long patronId = 1L;

        given(borrowingService.borrowBook(bookId, patronId)).willThrow(BookNotFoundException.class);


        // When
        ResultActions response = mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId));

        // Then
        response.andDo(print()).
            andExpect(status().isNotFound());
    }

    @Test
    public void givenBookIdAndPatronId_whenBorrowAndPatronDoesNotExists_thenReturn404() throws Exception {
        // Given
        long bookId = 1L;
        long patronId = 1L;

        given(borrowingService.borrowBook(bookId, patronId)).willThrow(PatronNotFoundException.class);

        // When
        ResultActions response = mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId));

        // Then
        response.andDo(print()).
            andExpect(status().isNotFound());
    }

    @Test
    public void givenBookIdAndPatronId_whenBorrowBookWithZeroQuantity_thenReturn422() throws Exception {
        // Given
        long bookId = 1L;
        long patronId = 1L;

        given(borrowingService.borrowBook(bookId, patronId)).willThrow(BookOutOfStockException.class);

        // When
        ResultActions response = mockMvc.perform(post("/api/borrow/{bookId}/patron/{patronId}", bookId, patronId));

        // Then
        response.andDo(print()).
            andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void givenBookIdAndPatronId_whenReturnBook_thenReturn200() throws Exception {
        // Given
        long bookId = 1L;
        long patronId = 1L;

        willDoNothing().given(borrowingService).returnBook(bookId, patronId);

        // When
        ResultActions response = mockMvc.perform(put("/api/return/{bookId}/patron/{patronId}", bookId, patronId));

        // Then
        response.andDo(print()).
            andExpect(status().isOk());

    }

    @Test
    public void givenBookIdAndPatronId_whenReturnBook_thenReturn404() throws Exception {
        // Given
        long bookId = 1L;
        long patronId = 1L;

        willThrow(BorrowNotFoundException.class).given(borrowingService).returnBook(bookId, patronId);

        // When
        ResultActions response = mockMvc.perform(put("/api/return/{bookId}/patron/{patronId}", bookId, patronId));

        // Then
        response.andDo(print()).
            andExpect(status().isNotFound());

    }
}
