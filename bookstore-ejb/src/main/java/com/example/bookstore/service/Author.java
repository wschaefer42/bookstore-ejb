package com.example.bookstore.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "authors")
public class Author {
    @NotBlank @Column(length = 100)
    private String firstName;
    @NotBlank @Column(length = 100)
    private String lastName;
    @Column(columnDefinition = "text")
    private String bio = null;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = null;

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getName() {
        return String.format("%s, %s", lastName, firstName);
    }
}
