package com.example.bookstore.service;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBeanBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Log
@Startup
@Singleton
public class DataLoader {
    public record AuthorRecord(String firstName, String lastName) {}

    @Getter
    public static class BookRecord {
        @CsvBindByName
        private String title;
        @CsvBindByName
        private String authors;
        @CsvBindByName
        private Short published;
        @CsvBindByName
        private String category;
        @CsvBindByName
        private String keywords;
        public List<AuthorRecord> getAuthorRecords() {
            var authorRecords = new ArrayList<AuthorRecord>();
            for (String author : Arrays.stream(authors.split(",")).map(String::trim).toList()) {
                if (StringUtils.indexOf(author, " ") > 0) {
                    authorRecords.add(new AuthorRecord(
                            StringUtils.substringBefore(author, " ").trim(),
                            StringUtils.substringAfter(author, " ").trim()
                    ));
                }
            }
            return authorRecords;
        }
        public List<String> getKeywordList() {
            return Arrays.stream(keywords.split(",")).map(String::trim).toList();
        }
    }

    @Inject
    @ConfigProperty(name = "load.data", defaultValue = "false")
    private boolean loadData;

    @PersistenceContext(unitName = "bookstore")
    private EntityManager em;

    @Transactional
    @PostConstruct
    void init() {
        if (loadData) {
            initDB(true);
        }
    }

    @Transactional
    public int initDB(boolean deleteAll) {
        int count = 0;
        if (deleteAll) {
            em.createNativeQuery("truncate table books restart identity cascade").executeUpdate();
        }
        for (BookRecord bookRecord : loadBookRecords()) {
            if (em.createQuery("select b from books b where b.title = ?1 and b.published = ?2", Book.class)
                    .setParameter(1, bookRecord.title)
                    .setParameter(2, bookRecord.published)
                    .getResultList().isEmpty()) {
                // Category
                var category = em.createQuery("select c from categories c where c.name = ?1", Category.class)
                        .setParameter(1, bookRecord.category)
                        .getResultList().stream().findFirst().orElse(null);
                if (category == null) {
                    category = new Category();
                    category.setName(bookRecord.category);
                    em.persist(category);
                }
                // Init Book
                var book = new Book();
                book.setTitle(bookRecord.title);
                book.setPublished(bookRecord.published);
                book.setCategory(category);
                book.setKeywords(bookRecord.getKeywordList());
                book.setPrice(BigDecimal.valueOf(new Random().nextDouble(100.0)));
                // Author
                var authors = new ArrayList<Author>();
                for (AuthorRecord authorRecord : bookRecord.getAuthorRecords()) {
                    var author = em.createQuery("select a from authors a where a.firstName = ?1 and a.lastName = ?2", Author.class)
                            .setParameter(1, authorRecord.firstName)
                            .setParameter(2, authorRecord.lastName)
                            .getResultList().stream().findFirst().orElse(null);
                    if (author == null) {
                        author = new Author(authorRecord.firstName, authorRecord.lastName);
                        em.persist(author);
                    }
                    authors.add(author);
                }
                // Save book
                book.setAuthors(authors);
                em.persist(book);
                em.flush();
                count++;
            }
        }
        em.flush();
        log.info("Added " + count + " books");
        return count;
    }

    private List<BookRecord> loadBookRecords() {
        try(var inputStream = this.getClass().getResourceAsStream("/data/books.csv")) {
            if (inputStream != null) {
                return new CsvToBeanBuilder<BookRecord>(new InputStreamReader(inputStream))
                        .withType(BookRecord.class)
                        .build()
                        .parse();
            } else {
                return List.of();
            }
        } catch (Exception ex) {
            throw new RuntimeException(String.format("Loading Books.csv failed with %s", ex.getMessage()));
        }
    }
}
