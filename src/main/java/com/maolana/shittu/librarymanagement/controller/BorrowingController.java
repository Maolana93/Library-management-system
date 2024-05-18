package com.maolana.shittu.librarymanagement.controller;


import com.maolana.shittu.librarymanagement.model.Borrow;
import com.maolana.shittu.librarymanagement.service.BorrowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BorrowingController {

     public final BorrowingService borrowingService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public Borrow borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        return borrowingService.borrowBook(bookId, patronId);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public void returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingService.returnBook(bookId, patronId);
    }

}
