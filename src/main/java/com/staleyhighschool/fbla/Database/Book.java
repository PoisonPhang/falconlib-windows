package com.staleyhighschool.fbla.Database;

import com.staleyhighschool.fbla.Library;

import java.sql.Connection;

/**
 * Add/Delete books from library
 * @author PoisonPhang
 */
public class Book {

    private String bookName;
    private String bookAuthor;
    private String bookID;

    private Connection connection;

    public Book(String bookName, String bookAuthor, String bookID) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookID = bookID;
        connection = Library.connection.getConnection();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public static void addBook() {
<<<<<<< Updated upstream
        // TODO add book to DB
=======

>>>>>>> Stashed changes
    }
}
