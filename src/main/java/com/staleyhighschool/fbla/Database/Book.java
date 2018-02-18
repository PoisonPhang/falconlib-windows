package com.staleyhighschool.fbla.Database;

import com.staleyhighschool.fbla.Users.User;
import com.staleyhighschool.fbla.util.Enums;

/**
 * Add/Delete books from library
 *
 * @author PoisonPhang
 */
public class Book {

    private String bookTitle;
    private String bookAuthor;
    private String bookID;
    private Enums.IsOut isOut;
    private Enums.IsLate isLate;

    public Book(String bookTitle, String bookAuthor, String bookID, Enums.IsLate isLate, Enums.IsOut isOut) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookID = bookID;
        this.isLate = isLate;
        this.isOut = isOut;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
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

    public boolean isLate(User user) {
        boolean late = false;

        if (isLate == Enums.IsLate.LATE) {
            late = false;
        } else {
            late = false;
        }
        return late;
    }

    public static void addBook() {
        // TODO add book to DB
    }
}
