package com.maolana.shittu.librarymanagement.repository;


import com.maolana.shittu.librarymanagement.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    @Query("select b from Borrow b where b.book.id = :bookId and b.patron.id = :patronId and b.isReturned = false")
    List<Borrow> findIfAlreadyBorrowed(@Param("bookId") Long bookId, @Param("patronId") Long patronId);

}
