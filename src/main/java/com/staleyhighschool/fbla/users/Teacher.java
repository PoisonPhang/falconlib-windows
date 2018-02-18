package com.staleyhighschool.fbla.users;

import com.staleyhighschool.fbla.library.Book;
import com.staleyhighschool.fbla.library.Library;
import com.staleyhighschool.fbla.util.Enums;

import java.util.List;

public class Teacher extends User {

    private double fineRate;
    private int lateBooks;
    private List<Book> userBooks;

    public Teacher(String sFirstName, String sLastName, String tID) {
        super(sFirstName, sLastName, tID, Enums.AccountType.aTEACHER);
        fineRate = Library.grabFineRate(this);
    }

    @Override
    public List<Book> getUserBooks() {
        return userBooks;
    }

    @Override
    public void setUserBooks() {
        userBooks = Library.connection.getUserBooks(this);
    }

    @Override
    public void setLateBooks() {
        lateBooks = Library.lateBookCount(this);
    }

    @Override
    public double calculateFine() {
        return lateBooks * fineRate;
    }
}
