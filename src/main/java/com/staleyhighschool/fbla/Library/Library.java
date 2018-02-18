package com.staleyhighschool.fbla.Library;

import com.staleyhighschool.fbla.Database.Connector;
import com.staleyhighschool.fbla.Users.User;
import com.staleyhighschool.fbla.util.Enums;

/**
 * Drives most the larger actions taken in the application
 */
public class Library {

    public static Connector connection;

    /**
     * Creates the only instance of the connector object
     */
    public Library() {
        connection = new Connector();
    }

    /**
     * Gets the current fine rate given the type of user
     * @param user {@link User}
     * @return {@link Double}
     */
    public static double grabFineRate(User user) {
        double fineRate = 0;
        if (user.getAccountType() == Enums.AccountType.aSTUDENT) {
            // TODO grab student fine rate from DB
        } else if (user.getAccountType() == Enums.AccountType.aTEACHER) {
            // TODO grab teacher fine rate from DB
        }
        return fineRate;
    }

    /**
     * Generates a count of overdue books for the given user
     * @param user {@link User}
     * @return {@link Integer}
     */
    public static int lateBookCount(User user) {
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
