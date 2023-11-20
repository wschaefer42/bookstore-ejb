package com.example.bookstore.service;

import java.io.Serializable;

public record CatalogQuery(String title, String author) implements Serializable{
}
