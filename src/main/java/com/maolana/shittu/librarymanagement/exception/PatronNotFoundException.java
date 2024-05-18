package com.maolana.shittu.librarymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PatronNotFoundException extends RuntimeException {
    public PatronNotFoundException(Long id) {
        super("Patron with id " + id + " does not exist.");
    }
}
