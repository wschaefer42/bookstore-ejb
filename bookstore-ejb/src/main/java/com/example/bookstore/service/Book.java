package com.example.bookstore.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Entity(name = "books")
public class Book {
    @NotBlank
    @Column(unique = true)
    private String title;
    private Short published;
    private BigDecimal price;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Author> authors;
    @ManyToOne
    private Category category;
    @ElementCollection
    private List<String> keywords;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    public List<String> getAuthorList() {
        return authors.stream().map(a -> String.format("%s, %s", a.getLastName(), a.getFirstName())).toList();
    }
}
