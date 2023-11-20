package com.example.bookstore.service;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "categories")
public class Category {
    @NotBlank
    @Column(unique = true)
    private String name;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid = null;
}
