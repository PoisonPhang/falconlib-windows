package com.staleyhighschool.fbla.Users;

import com.staleyhighschool.fbla.Library.Library;
import com.staleyhighschool.fbla.util.Enums;

public class Student extends User {

    private double fineRate;
    private int lateBooks;

    public Student(String sFirstName, String sLastName, String sID) {
        super(sFirstName, sLastName, sID, Enums.AccountType.aSTUDENT);
        fineRate = Library.grabFineRate(this);
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
