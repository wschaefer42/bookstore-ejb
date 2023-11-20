package com.example.bookstore.service;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

@Path("/catalog")
public class CatalogResource {
    public record BookRecord(
            long id, String title, short published, String category, List<String> authors, String keywords) {
    }

    @EJB
    private Catalog catalog;

    @EJB
    private DataLoader dataLoader;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookRecord> list() {
        return catalog.searchBooks(null).stream().map(b ->
                new BookRecord(
                        b.getId(),
                        b.getTitle(),
                        b.getPublished(),
                        b.getCategory(),
                        Arrays.asList(b.getAuthors()),
                        StringUtils.joinWith(", ", Arrays.stream(b.getKeywords()).toArray())
                )
        ).toList();
    }

    @PUT
    @Path("init")
    public Response initDB(@DefaultValue("false") @QueryParam("deleteAll") boolean deleteALl) {
        int addedCount = dataLoader.initDB(deleteALl);
        return Response
                .status(Response.Status.CREATED)
                .entity(String.format("Added %d books", addedCount))
                .build();
    }
}
