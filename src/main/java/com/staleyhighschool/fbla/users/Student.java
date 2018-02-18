package com.staleyhighschool.fbla.users;

import com.staleyhighschool.fbla.library.Book;
import com.staleyhighschool.fbla.library.Library;
import com.staleyhighschool.fbla.util.Enums;

import java.util.List;

public class Student extends User {

    private double fineRate;
    private int lateBooks;
    private List<Book> userBooks;

    public Student(String sFirstName, String sLastName, String sID) {
        super(sFirstName, sLastName, sID, Enums.AccountType.aSTUDENT);
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