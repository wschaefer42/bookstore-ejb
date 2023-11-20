package com.example.bookstore.service;

import jakarta.ejb.Remote;

@Remote
public interface ShoppingCard {
    void add(BookItem item);
    void checkout();
}
