package com.staleyhighschool.fbla.Database.Users;

public class Student extends User{

    private String sFirstName;
    private String sLastName;
    private String sID;

    public Student(String sFirstName, String sLastName, String sID) {
        super(sFirstName, sLastName, sID);
    }
}
