package com.staleyhighschool.fbla;

import com.staleyhighschool.fbla.Database.Book;
import com.staleyhighschool.fbla.Database.Connector;
import com.staleyhighschool.fbla.Database.Users.User;
import com.staleyhighschool.fbla.util.Enums;

import java.util.ArrayList;
import java.util.List;

public class Library {

    public static Connector connection;

    public Library() {
        connection = new Connector();
    }

    public static double grabFineRate(User user) {
        double fineRate = 0;
        if (user.getAccountType() == Enums.AccountType.aSTUDENT) {
            // TODO grab student fine rate from DB
        } else if (user.getAccountType() == Enums.AccountType.aTEACHER) {
            // TODO grab teacher fine rate from DB
        }
        return fineRate;
    }

    public static int lateBookCount(User user) {
        List<Book> lateBooks = new ArrayList<>();
        int lateBookCount = 0;
        for (Book book : user.getUserBooks()) {
            if (book.isLate(user)) {
                lateBookCount++;
            }
        }

        return lateBookCount;
    }

    public void saveFineRate() {
        // TODO logic for deciding account type rate and setting it
    }
}
