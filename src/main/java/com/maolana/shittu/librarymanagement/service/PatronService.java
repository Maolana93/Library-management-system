package com.maolana.shittu.librarymanagement.service;


import com.maolana.shittu.librarymanagement.exception.PatronNotFoundException;
import com.maolana.shittu.librarymanagement.model.Patron;
import com.maolana.shittu.librarymanagement.repository.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class PatronService {


   public final PatronRepository patronRepository;

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Patron getPatronById(Long id) {
        return patronRepository.findById(id).orElseThrow(() -> new PatronNotFoundException(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Patron addPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Patron updatePatron(Long id, Patron newPatron) {

        Patron patron = getPatronById(id);
        patron.setName(Objects.toString(newPatron.getName(), patron.getName()));
        patron.setAddress(Objects.toString(newPatron.getAddress(), patron.getAddress()));
        patron.setPhoneNumber(Objects.toString(newPatron.getPhoneNumber(), patron.getPhoneNumber()));
        patron.setEmailAddress(Objects.toString(newPatron.getEmailAddress(), patron.getEmailAddress()));

        return patronRepository.save(patron);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deletePatron(Long id) {
        patronRepository.deleteById(id);
    }

}
