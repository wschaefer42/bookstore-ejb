package com.example.bookstore.service;

import jakarta.ejb.Remote;
import java.util.List;

@Remote
public interface Catalog {
    List<BookItem> searchBooks(CatalogQuery query);
}
