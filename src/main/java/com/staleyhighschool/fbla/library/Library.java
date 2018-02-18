package com.staleyhighschool.fbla.library;

import com.staleyhighschool.fbla.database.Connector;
import com.staleyhighschool.fbla.users.Student;
import com.staleyhighschool.fbla.users.Teacher;
import com.staleyhighschool.fbla.users.User;
import com.staleyhighschool.fbla.util.Enums;

import java.util.List;
import java.util.UUID;

/**
 * Drives most the larger actions taken in the application
 */
public class Library {

    private String TAG = (this.getClass().getName() + ": ");

    public static Connector connection;
    public static List<User> userList;
    public static List<Book> bookList;

    /**
     * Creates the only instance of the connector object
     */
    public Library() {
        connection = new Connector();
        userList = loadUserList();
        bookList = loadBookList();
    }

    /**
     * Gets the current fine rate given the type of user
     * @param user {@link User}
     * @return {@link Double}
     */
    public static double grabFineRate(User user) {
        double fineRate = 0;
        connection.getFineRate(user.getAccountType());
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
            if (book.isLate()) {
                lateBookCount++;
            }
        }

        return lateBookCount;
    }

    private List<Book> loadBookList() {
        return connection.getLibraryBooks();
    }

    private List<User> loadUserList() {
        return connection.getCurrentUsers();
    }

    public void addUser(String firstName, String lastName, boolean tOrS) {
        User user;

        if (tOrS) {
            user = new Teacher(firstName, lastName, generateNewID());
        } else {
            user = new Student(firstName, lastName, generateNewID());
        }

        connection.addUser(user);
        userList.add(user);
    }

    public void addBook(String title, String author) {
        Book book;

        book = new Book(title, author, generateNewID(), Enums.IsLate.SAFE, Enums.IsOut.IN);

        connection.addBook(book);
        bookList.add(book);
    }

    private String generateNewID() {
        String id = UUID.randomUUID().toString();
        id = id.replaceAll("-", "");
        return id;
    }

    public void saveFineRate() {
        // TODO logic for deciding account type rate and setting it
    }
}
