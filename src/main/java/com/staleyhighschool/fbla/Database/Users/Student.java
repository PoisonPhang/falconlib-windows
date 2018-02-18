package com.staleyhighschool.fbla.Database.Users;

import com.staleyhighschool.fbla.Database.Book;
import com.staleyhighschool.fbla.Library;
import com.staleyhighschool.fbla.util.Enums;

import java.util.List;

public class Student extends User {

    private double fineRate;
    private int lateBooks;

    public Student(String sFirstName, String sLastName, String sID) {
        super(sFirstName, sLastName, sID, Enums.AccountType.aSTUDENT);
        fineRate = Library.grabFineRate(this);
    }

    @Override
    public void setuserBooks() {
        Library.connection.getUserBooks(this);
    }

    @Override
    public void setLateBooks() {
        lateBooks = Library.lateBookCount(this);
    }

    @Override
    public double calculateFine(double fineRate) {
        return  lateBooks * fineRate;
    }
}
