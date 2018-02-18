package com.staleyhighschool.fbla.Database.Users;

import com.staleyhighschool.fbla.Library;
import com.staleyhighschool.fbla.util.Enums;

public class Student extends User {

    private double fineRate;
    private int lateBooks;

    public Student(String sFirstName, String sLastName, String sID) {
        super(sFirstName, sLastName, sID, Enums.AccountType.aSTUDENT);
        fineRate = Library.grabFineRate(this);
    }

    @Override
    public double calculateFine(double fineRate) {
        return Library.lateBookCount(this) * fineRate;
    }
}
