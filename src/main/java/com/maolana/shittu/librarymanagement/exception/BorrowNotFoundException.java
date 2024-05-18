package com.maolana.shittu.librarymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BorrowNotFoundException extends RuntimeException {
    public BorrowNotFoundException(Long bookId, Long patronId) {
        super("Book with id " + bookId + " is not borrowed by patronId with id " + patronId);
    }
}
