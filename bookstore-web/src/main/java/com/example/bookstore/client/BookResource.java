package com.example.bookstore.client;

import com.example.bookstore.service.BookItem;
import com.example.bookstore.service.Catalog;
import com.example.bookstore.service.CatalogQuery;
import jakarta.ejb.EJB;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.extern.java.Log;
import java.util.List;

@Log
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {
    @EJB
    private Catalog catalog;

    @GET
    public List<BookItem> list() {
        return catalog.searchBooks(null);
    }

    @GET
    @Path("search")
    public List<BookItem> search(@BeanParam BookQuery query) {
        return catalog.searchBooks(new CatalogQuery(
                query.getTitle(),
                query.getAuthor()
        ));
    }
}
