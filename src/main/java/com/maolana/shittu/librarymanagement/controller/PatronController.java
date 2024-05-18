package com.maolana.shittu.librarymanagement.controller;


import com.maolana.shittu.librarymanagement.model.Patron;
import com.maolana.shittu.librarymanagement.service.PatronService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patrons")
public class PatronController {


    public final PatronService patronService;

    @GetMapping
    public List<Patron> getAllPatrons() {
        return patronService.getAllPatrons();
    }

    @GetMapping("/{id}")
    public Patron getPatron(@PathVariable Long id) {
        return patronService.getPatronById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patron addPatron(@RequestBody Patron patron) {
        return patronService.addPatron(patron);
    }

    @PutMapping("/{id}")
    public Patron updatePatron(@PathVariable Long id, @RequestBody Patron newPatron) {
        return patronService.updatePatron(id, newPatron);
    }

    @DeleteMapping("/{id}")
    public void deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
    }

}
