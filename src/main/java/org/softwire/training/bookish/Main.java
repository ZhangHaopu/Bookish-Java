package org.softwire.training.bookish;

import org.apache.tomcat.util.buf.StringCache;
import org.jdbi.v3.core.Jdbi;
import org.softwire.training.bookish.models.database.Book;

import java.sql.*;
import java.util.List;


public class Main {

    public static void main(String[] args) throws SQLException {
        String hostname = "localhost";
        String database = "mydb";
        String user = "root";
        String password = "password";
        String connectionString = "jdbc:mysql://" + hostname + "/" + database + "?user=" + user + "&password=" + password + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false";

        jdbcMethod(connectionString);
        jdbiMethod(connectionString);
    }

    private static void jdbcMethod(String connectionString) throws SQLException {
        System.out.println("JDBC method...");

        // TODO: print out the details of all the books (using JDBC)
        // See this page for details: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html

        Connection connection = DriverManager.getConnection(connectionString);
        String query = "select id_books, title, author from Books";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id_books = rs.getInt("id_books");
                String title = rs.getString("title");
                String author = rs.getString("author");
                System.out.println("bookids: " + String.valueOf(id_books) + " title: " + title + " author: " + author);
            }
        } catch (SQLException e) {
            System.out.println("error occured");
        }
    }

    private static void jdbiMethod(String connectionString) {
        System.out.println("\nJDBI method...");
        // TODO: print out the details of all the books (using JDBI)
        // See this page for details: http://jdbi.org
        // Use the "Book" class that we've created for you (in the models.database folder)

        Jdbi jdbi = Jdbi.create(connectionString);

        List<Book> books = jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM Books")
                    .mapToBean(Book.class)
                    .list();
        });

        for(Book book: books)
            System.out.println("bookids: " + String.valueOf(book.id_books) + " title: " + book.title + " author: " + book.author);
    }
}
