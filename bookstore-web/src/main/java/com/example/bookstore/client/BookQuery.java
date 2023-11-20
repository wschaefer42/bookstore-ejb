package com.example.bookstore.client;

import jakarta.ws.rs.QueryParam;
import lombok.Getter;

@Getter
public class BookQuery {
    @QueryParam("title")
    private String title = null;
    @QueryParam("author")
    private String author = null;
}
