package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Book;
import org.softwire.training.bookish.models.database.CopyWithLoanInfo;
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

    public List<CopyWithLoanInfo> getCopiesByBookId(int book_id){
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT id_copyofbooks AS copy_id, available, members.name AS member_name, transactions.outDate AS out_date, transactions.dueDate as due_date " +
                        "FROM copiesOfBook " +
                        "LEFT JOIN transactions ON copiesOfBook.id_copyofbooks = transactions.bookid " +
                        "LEFT JOIN members ON transactions.userid = members.id_members " +
                        "WHERE copiesOfBook.id = :book_id " +
                        "AND copiesOfBook.deletedDate IS NULL " +
                        "AND transactions.returnDate IS NULL")
                        .bind("book_id", book_id)
                        .mapToBean(CopyWithLoanInfo.class)
                        .list()
                );

    }

    public void addBook(String title, String author, String isbn) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO books (title, author, isbn) VALUES (:title, :author, :isbn)")
                        .bind("title", title)
                        .bind("author", author)
                        .bind("isbn", isbn)
                        .execute()
        );
    }

    public void addCopy(int book_id){
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO copiesOfBook (id, available) VALUES (:book_id, 1) SELECT LAST_INSERT_ID()")
                        .bind("book_id", book_id)
                        .execute()
        );
    }

    public void editBook(int book_id, String author, String title, String isbn) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE books SET author = :author, title = :title, isbn = :isbn WHERE id_books = :book_id")
                        .bind("book_id", book_id)
                        .bind("author", author)
                        .bind("title", title)
                        .bind("isbn", isbn)
                        .execute()
        );

    }


}
