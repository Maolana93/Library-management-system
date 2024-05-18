package com.maolana.shittu.librarymanagement.exception;


import com.maolana.shittu.librarymanagement.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class BookOutOfStockException extends RuntimeException {
    public BookOutOfStockException(Book book) {
        super("The book " + book.getTitle() + " is out of stock!");
    }
}
