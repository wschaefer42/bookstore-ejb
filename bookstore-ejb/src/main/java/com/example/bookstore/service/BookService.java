package com.example.bookstore.service;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@ApplicationScoped
public class BookService {
    @PersistenceContext(unitName = "bookstore")
    private EntityManager em;

    public List<Book> findAll() {
        return em.createQuery("select b from books b", Book.class).getResultList();
    }
}
