package com.staleyhighschool.fbla.library;

import com.staleyhighschool.fbla.database.Connector;
import com.staleyhighschool.fbla.users.Student;
import com.staleyhighschool.fbla.users.Teacher;
import com.staleyhighschool.fbla.users.User;
import com.staleyhighschool.fbla.util.Enums;

import java.util.List;
import java.util.Random;
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
     *
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
     *
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

    public Book addBook(String title, String author) {
        Book book;

        book = new Book(title, author, generateNewID(), Enums.IsLate.SAFE, Enums.IsOut.IN, Book.storeDate);

        connection.addBook(book);
        bookList.add(book);
        return book;
    }

    public void deleteBook(List<Book> books) {
        System.out.println(TAG + "ripp " + books.size());
        for (Book book : books) {
            System.out.println(TAG + book.isOut());
            if (book.isOut()) {
                // TODO Tell user book can not be deleted
            } else if (!book.isOut()){
                System.out.println(TAG + "Books almost yeeted");
                connection.deleteBook(book);

                for (int i = 0; i < bookList.size(); i++) {
                    bookList.remove(book);
                }
            }
        }
    }

    public void checkOutBook(User user, Book book) {
        if (user.getUserBooks().size() - 2 <= connection.getMaxBooks(user)) {
                if (!book.isOut()) {
                    connection.userCheckOut(user, book);

                    book.setIsOut(Enums.IsOut.OUT);
                }
        }
    }

    public void returnBook(List<Book> selectedBooks) {
        for (Book selectedBook : selectedBooks) {
            if (selectedBook.isOut()) {
                connection.userReturnBook(selectedBook);

                selectedBook.setIsOut(Enums.IsOut.IN);
            }
        }
    }

    private String generateNewID() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        Random rnd = new Random();

        boolean pass = false;

        StringBuilder sb;

        do {
            sb = new StringBuilder((100000 + rnd.nextInt(900000)) + "-");
            for (int i = 0; i < 5; i++) {
                sb.append(chars[rnd.nextInt(chars.length)]);
            }

            pass = connection.checkValidID(sb.toString());
        } while (!pass);

        return sb.toString();
    }

}
