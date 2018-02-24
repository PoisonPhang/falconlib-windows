package com.staleyhighschool.fbla.database;

import com.staleyhighschool.fbla.library.Book;
import com.staleyhighschool.fbla.library.Library;
import com.staleyhighschool.fbla.users.Student;
import com.staleyhighschool.fbla.users.Teacher;
import com.staleyhighschool.fbla.users.User;
import com.staleyhighschool.fbla.util.Enums;

import java.sql.*;
import java.util.Date;
import java.util.List;

/**
 * Establishes connection with the database, gets values from database, changes values in database, and
 * adds values to database.
 */
public class Connector {

    private String TAG = (this.getClass().getName() + ": ");

    public static Connection connection;

    private final String DATABASE_NAME = "";
    private final String DATABASE_URL = "";
    private final String PORT = ":";

    /**
     * Establishes connection to database
     */
    public Connector() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL + PORT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the existing connection
     * @return existing {@link Connection}
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Gets list of all books in the library
     * @return {@link List<Book>}
     */
    public List<Book> getLibraryBooks() {

        String query = "select TITLE, AUTHOR, ID, IS_OUT, IS_LATE, DATE_OUT " + "from " + DATABASE_NAME + ".LIBRARY_BOOKS";

        return setBooks(query);
    }

    /**
     * Gets list of books that the given {@link User} has
     * @param user {@link User}
     * @return {@link List<Book>}
     */
    public List<Book> getUserBooks(User user) {

        List<Book> userBooks = null;
        Book book;

        String bookTitle;
        String bookAuthor;
        String bookID;
        Enums.IsLate isLate = null;
        Enums.IsOut isOut = null;

        String query = "select ID, TIME_OUT " + "from " + DATABASE_NAME + "." + user.getUserID();

        Statement statement;
        ResultSet resultSet;

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                for (int i = 0; i < Library.bookList.size(); i++) {
                    if (resultSet.getString("ID").equals(Library.bookList.get(i).getBookID())) {
                        userBooks.add(Library.bookList.get(i));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userBooks;
    }

    /**
     * Grabs a list of books from a selected table
     * @param query {@link String}
     * @return {@link List<Book>}
     */
    private List<Book> setBooks(String query) {

        Statement statement;
        ResultSet resultSet;

        String bookTitle;
        String bookAuthor;
        String bookID;
        Enums.IsLate isLate = null;
        Enums.IsOut isOut = null;
        Date dateOut;

        List<Book> books = null;
        Book book;

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                if (resultSet.getString("IS_LATE").equals("late")) {
                    isLate = Enums.IsLate.LATE;
                } else if (resultSet.getString("IS_OUT").equals("out")) {
                    isOut = Enums.IsOut.OUT;
                }

                bookTitle = resultSet.getString("TITLE");
                bookAuthor = resultSet.getString("AUTHOR");
                bookID = resultSet.getString("ID");
                dateOut = resultSet.getDate("DATE_OUT");

                book = new Book(bookTitle, bookAuthor, bookID, isLate, isOut, dateOut);
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public double getFineRate(Enums.AccountType accountType) {

        Statement statement;
        ResultSet resultSet;

        String query;
        double fineRate = 0;

        query = "select STUDENT, TEACHER from " + DATABASE_NAME + ".RULES";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if (accountType == Enums.AccountType.aTEACHER) {
                    fineRate = resultSet.getDouble("TEACHER");
                } else if (accountType == Enums.AccountType.aSTUDENT) {
                    fineRate = resultSet.getDouble("STUDENT");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fineRate;
    }

    public void addUser(User user) {
        String accountType = "";

        Statement statement;

        if (user.getAccountType() == Enums.AccountType.aTEACHER) {
            accountType = "teacher";
        } else if (user.getAccountType() == Enums.AccountType.aSTUDENT) {
            accountType = "student";
        }

        String addToUsersQuery = "INSERT INTO USERS (FIRST_NAME, LAST_NAME, ID, ACCOUNT_TYPE) " +
                "VALUES (" + user.getFirstName() +
                ", " + user.getLastName() +
                ", " + user.getUserID() +
                ", " + accountType + ")";

        String createTableQuery = "CREATE TABLE " + user.getUserID() + " (BOOKS string)";

        try {

            statement = connection.createStatement();

            statement.executeUpdate(addToUsersQuery);
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBook(Book book) {
        // TODO add book to DB logic
    }

    public List<User> getCurrentUsers() {

        List<User> users = null;

        User user;

        String firstName, lastName, id;
        Enums.AccountType accountType =  null;

        Statement statement;
        ResultSet resultSet;

        String query = "select FIRST_NAME, LAST_NAME, ID, ACCOUNT_TYPE from " + DATABASE_NAME + ".USERS";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                if (resultSet.getString("ACCOUNT_TYPE").equals("teacher")) {
                    accountType = Enums.AccountType.aTEACHER;
                } else if (resultSet.getString("ACCOUNT_TYPE").equals("student")) {
                    accountType = Enums.AccountType.aSTUDENT;
                }

                firstName = resultSet.getString("FIRST_NAME");
                lastName = resultSet.getString("LAST_NAME");
                id = resultSet.getString("ID");

                if (accountType == Enums.AccountType.aTEACHER) {
                    user = new Teacher(firstName, lastName, id);
                    users.add(user);
                } else if (accountType == Enums.AccountType.aSTUDENT) {
                    user = new Student(firstName, lastName, id);
                    users.add(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public int getMaxDays(User user) {
        int days = 0;

        if (user.getAccountType() == Enums.AccountType.aTEACHER) {
            // TODO add db.getTeacherMaxDays
        } else if (user.getAccountType() == Enums.AccountType.aSTUDENT) {
            // TODO add db.getStudentMaxDays
        }
        return days;
    }
}
