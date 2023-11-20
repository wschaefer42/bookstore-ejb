package com.example.bookstore.service;

import jakarta.ejb.EJB;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@Stateless
@Remote(Catalog.class)
public class CatalogBean implements Catalog {
    @Inject
    private BookService bookService;

    @Override
    public List<BookItem> searchBooks(CatalogQuery query) {
        List<BookItem> bookItems = new ArrayList<>();
        for (Book book : findBooks(query)) {
            bookItems.add(BookItem.builder()
                    .id(book.getId())
                    .title(book.getTitle())
                    .published(book.getPublished())
                    .price(book.getPrice())
                    .authors(listAuthors(book))
                    .keywords(book.getKeywords().toArray(new String[0]))
                    .build()
            );
        }
        return bookItems;
    }

    private String[] listAuthors(Book book) {
        return book.getAuthors().stream().map(Author::getName).toList().toArray(String[]::new);
    }

    private List<Book> findBooks(CatalogQuery query) {
        List<Book> books;
        if (query == null) {
            books = bookService.findAll();
        } else {
            books = List.of();
        }
        return books;
    }
}
