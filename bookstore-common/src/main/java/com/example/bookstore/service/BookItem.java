package com.example.bookstore.service;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Builder
public class BookItem implements Serializable {
    private Long id;
    private String title;
    private String[] authors;
    private Short published;
    private String category;
    private String[] keywords;
    private BigDecimal price;
}
