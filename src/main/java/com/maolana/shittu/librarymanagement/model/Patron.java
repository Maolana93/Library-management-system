package com.maolana.shittu.librarymanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "Patrons")
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 250, nullable = false)
    @NotNull(message = "Patron name must not be Null")
    private String name;

    @Column(name = "address", length = 300)
    private String address;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @Column(name = "email_address", length = 100)
    @Email(message = "Patron Email must be valid")
    private String emailAddress;

    @OneToMany(mappedBy = "patron", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Borrow> borrows;
}
