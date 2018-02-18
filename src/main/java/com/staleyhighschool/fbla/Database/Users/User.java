package com.staleyhighschool.fbla.Database.Users;

import com.staleyhighschool.fbla.Database.Book;
import com.staleyhighschool.fbla.util.Enums;

import java.util.List;

public abstract class User {
    private String firstName;
    private String lastName;
    private String userID;
    private Enums.AccountType accountType;
    private List<Book> userBooks;

    public User() {
        firstName = "";
        lastName = "";
        userID = "";
    }

    public User(String firstName, String lastName, String userID, Enums.AccountType accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
        this.accountType = accountType;
        userBooks = null;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
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

    public List<Book> getUserBooks() {
        return userBooks;
    }

    public void grabBooks() {
        // TODO grab users books from DB
    }

    public abstract double calculateFine(double fineRate);

    public Enums.AccountType getAccountType() {
        return accountType;
    }
}
