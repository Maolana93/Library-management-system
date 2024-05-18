package com.maolana.shittu.librarymanagement.service;


import com.maolana.shittu.librarymanagement.exception.BookOutOfStockException;
import com.maolana.shittu.librarymanagement.exception.BorrowNotFoundException;
import com.maolana.shittu.librarymanagement.model.Book;
import com.maolana.shittu.librarymanagement.model.Borrow;
import com.maolana.shittu.librarymanagement.model.Patron;
import com.maolana.shittu.librarymanagement.repository.BorrowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BorrowingService {


   public final BookService bookService;

   public final PatronService patronService;

   public final BorrowRepository borrowRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Borrow borrowBook(Long bookId, Long patronId) {
        Book book = bookService.getBookById(bookId);
        Patron patron = patronService.getPatronById(patronId);
        if (book.canBorrow()) {
            book.borrow();
            Borrow borrow = new Borrow(book, patron);
            borrowRepository.save(borrow);
            return borrow;
        }
        throw new BookOutOfStockException(book);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void returnBook(Long bookId, Long patronId) {
        Borrow borrow = findIfAlreadyBorrowed(bookId, patronId);
        borrow.doReturn();
        borrowRepository.save(borrow);
    }

    private Borrow findIfAlreadyBorrowed(Long bookId, Long patronId) {
        return borrowRepository.findIfAlreadyBorrowed(bookId, patronId).stream().findAny().orElseThrow(() -> new BorrowNotFoundException(bookId, patronId));
    }

}
