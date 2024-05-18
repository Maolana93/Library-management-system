package com.maolana.shittu.librarymanagement.repository;


import com.maolana.shittu.librarymanagement.model.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {

}
