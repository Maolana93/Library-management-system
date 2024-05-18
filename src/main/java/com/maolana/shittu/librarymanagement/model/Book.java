package com.maolana.shittu.librarymanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    @NotNull(message = "Enter book title")
    private String title;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "author", nullable = false)
    @NotNull(message = " Enter book  author ")
    private String author;

    @Column(name = "isbn", length = 50, nullable = false, unique = true)
    @NotNull(message = "Book ISBN must not be Null")
    private String isbn;

    @Column(name = "publication_year")
    @NotNull(message = " Enter book publication year ")
    private Timestamp publicationYear;

    private Integer quantity = 0;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Borrow> borrows;

    public boolean canBorrow() {
        return quantity > 0;
    }

    public void borrow() {
        if (canBorrow()) {
            quantity--;
        }
    }

    public void returnBook() {
        quantity++;
    }
}
