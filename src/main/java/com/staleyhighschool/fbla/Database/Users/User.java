package com.staleyhighschool.fbla.Database.Users;

import com.staleyhighschool.fbla.Database.Book;

import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String userID;
    private List<Book> userBooks;

    public User() {
        firstName = "";
        lastName = "";
        userID = "";
    }

    public User(String firstName, String lastName, String userID) {
        this.firstName = firstName;
        this.lastName = lastName;
        userBooks = null;
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void grabBooks() {
        // TODO grab users books from DB
    }
}
