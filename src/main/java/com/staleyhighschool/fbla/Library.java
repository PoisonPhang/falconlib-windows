package com.staleyhighschool.fbla;

import com.staleyhighschool.fbla.Database.Connector;

public class Library {

    public static Connector connection;

    public Library() {
        connection = new Connector();
    }
}
