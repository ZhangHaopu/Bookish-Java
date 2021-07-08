package org.softwire.training.bookish.models.database;

public class Book {
    public Integer id_books;
    public String title;
    public String author;

    public Integer getId_books() {
        return id_books;
    }

    public void setId_books(Integer id_books) {
        this.id_books = id_books;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
