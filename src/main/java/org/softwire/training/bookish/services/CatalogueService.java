package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.database.Technology;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogueService extends DatabaseService{
    public List<Book> getBooksByTitle(String title) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM Books WHERE title LIKE CONCAT('%',:title,'%')")
                        .bind("title", title)
                        .mapToBean(Book.class)
                        .list()
        );
    }
}
