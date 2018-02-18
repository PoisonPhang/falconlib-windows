package com.staleyhighschool.fbla.Users;

import com.staleyhighschool.fbla.Library.Book;
import com.staleyhighschool.fbla.util.Enums;

import java.util.List;

/**
 * General User type
 */
public abstract class User {
    private String firstName;
    private String lastName;
    private String userID;
    private Enums.AccountType accountType;

    public User() {
        firstName = "";
        lastName = "";
        userID = "";
    }

    /**
     * Generates a new user
     * @param firstName {@link String} holding the first name of the {@link User}
     * @param lastName {@link String} holding the last name of the {@link User}
     * @param userID {@link String} holding the ID of the {@link User}
     * @param accountType {@link com.staleyhighschool.fbla.util.Enums.AccountType} determining the type of account for the {@link User}
     */
    public User(String firstName, String lastName, String userID, Enums.AccountType accountType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userID = userID;
        this.accountType = accountType;
    }

    /**
     * Gets the ID of the {@link User}
     * @return {@link String} holding the ID of the {@link User}
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Gets the first name of the {@link User}
     * @return {@link String} holding the first name of the {@link User}
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the last name of the {@link User}
     * @return {@link String} holding the last name of the {@link User}
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the {@link List<Book>} held by the {@link User}
     * @return {@link List<Book>} help by the {@link User}
     */
    public abstract List<Book> getUserBooks();

    public abstract void setUserBooks();

    public abstract void setLateBooks();

    public abstract double calculateFine();

    public Enums.AccountType getAccountType() {
        return accountType;
    }
}