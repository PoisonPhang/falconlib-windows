package com.staleyhighschool.fbla.Database.Users;

import com.staleyhighschool.fbla.Library;
import com.staleyhighschool.fbla.util.Enums;

public class Teacher extends User {

    private double fineRate;
    private int lateBooks;

    public Teacher(String sFirstName, String sLastName, String tID) {
        super(sFirstName, sLastName, tID, Enums.AccountType.aSTUDENT);
        fineRate = Library.grabFineRate(this);
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
