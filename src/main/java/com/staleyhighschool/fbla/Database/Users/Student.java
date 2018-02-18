package com.staleyhighschool.fbla.Database.Users;

import com.staleyhighschool.fbla.Library;

public class Student extends User {

    private double fineRate;

    public Student(String sFirstName, String sLastName, String sID) {
        super(sFirstName, sLastName, sID, AccountType.aSTUDENT);
        fineRate = Library.grabFineRate(this);
    }
}
