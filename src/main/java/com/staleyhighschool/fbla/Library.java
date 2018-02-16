package com.staleyhighschool.fbla;

import com.staleyhighschool.fbla.Database.Connector;
import com.staleyhighschool.fbla.Database.Users.User;

public class Library {

    public static Connector connection;

    public Library() {
        connection = new Connector();
    }

    public static double grabFineRate(User user) {
        double fineRate = 0;
        if (user.getAccountType() == User.AccountType.aSTUDENT) {
            // TODO grab student fine rate from DB
        } else if (user.getAccountType() == User.AccountType.aTEACHER) {
            // TODO grab teacher fine rate from DB
        }
        return fineRate;
    }

    public void saveFineRate() {
        // TODO logic for deciding account type rate and setting it
    }
}
