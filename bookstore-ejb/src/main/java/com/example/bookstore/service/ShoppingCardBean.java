package com.example.bookstore.service;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateful;

import java.util.ArrayList;
import java.util.List;

@Stateful
public class ShoppingCardBean implements ShoppingCard {
    private List<BookItem> items;

    @Override
    public void add(BookItem item) {
        items.add(item);
    }

    @Override
    public void checkout() {

    }

    @PostConstruct
    void init() {
        items = new ArrayList<>();
    }
}
