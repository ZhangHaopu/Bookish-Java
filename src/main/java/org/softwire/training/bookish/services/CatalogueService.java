package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.database.Copy;
import org.softwire.training.bookish.models.database.Technology;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogueService extends DatabaseService{
    public List<Book> getBooksByTitleAndAuthor(String title, String author) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM Books WHERE title LIKE CONCAT('%',:title,'%') AND author LIKE CONCAT('%', :title, '%')")
                        .bind("title", title)
                        .bind("author", author)
                        .mapToBean(Book.class)
                        .list()
        );
    }


    public void deleteCopyById(int copy_id){
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE copyOfBooks SET deletedDate = GETDATE() WHERE id_copyofbooks = :id")
                        .bind("id", copy_id)
                        .execute()
        );
    }

    public List<Copy> getCopiesByBookid(int book_id){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * From CopiesOfBook LEFT JOIN Transaction ON CopiesOfBook.id_copyofbooks = Transaction.bookid " +
                        "LEFT JOIN MEMBERS ON Transactions.userid = Members.id " +
                        "WHERE CopiesOfBook.id_copyofbooks = book_id AND CopiesOfBook.deletedDate IS NOT NULL AND Transactions.returnDate IS NULL")
                        .bind("book_id", book_id)
                        .mapTobean(Copy.class)
                        .list()
                );
    }





}
