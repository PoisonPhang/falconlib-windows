package com.staleyhighschool.fbla.Database.Users;

import com.staleyhighschool.fbla.Database.Book;
import com.staleyhighschool.fbla.Library;

import java.sql.Connection;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private String userID;
    private AccountType accountType;
    private List<Book> userBooks;

    public User() {
        firstName = "";
        lastName = "";
        userID = "";
    }

    public User(String firstName, String lastName, String userID, AccountType accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
        this.accountType = accountType;
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

    public double grabFine() {
        double fine = 0;
        // TODO add math for calculating fine
        return fine;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public enum AccountType {
        aSTUDENT, aTEACHER;
    }
}
