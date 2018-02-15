package com.staleyhighschool.fbla.Database.Users;

import com.staleyhighschool.fbla.Database.Book;

import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private List<Book> userBooks;

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        userBooks = null;
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
