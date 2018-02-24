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

        String query = "SELECT ID, TIME_OUT " + "FROM " + DATABASE_NAME + "." + user.getUserID();

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

        query = "SELECT STUDENT, TEACHER FROM " + DATABASE_NAME + ".RULES";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if (accountType == Enums.AccountType.TEACHER) {
                    fineRate = resultSet.getDouble("TEACHER");
                } else if (accountType == Enums.AccountType.STUDENT) {
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

        if (user.getAccountType() == Enums.AccountType.TEACHER) {
            accountType = "teacher";
        } else if (user.getAccountType() == Enums.AccountType.STUDENT) {
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

        String query = "SELECT FIRST_NAME, LAST_NAME, ID, ACCOUNT_TYPE FROM " + DATABASE_NAME + ".USERS";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                if (resultSet.getString("ACCOUNT_TYPE").equals("teacher")) {
                    accountType = Enums.AccountType.TEACHER;
                } else if (resultSet.getString("ACCOUNT_TYPE").equals("student")) {
                    accountType = Enums.AccountType.STUDENT;
                }

                firstName = resultSet.getString("FIRST_NAME");
                lastName = resultSet.getString("LAST_NAME");
                id = resultSet.getString("ID");

                if (accountType == Enums.AccountType.TEACHER) {
                    user = new Teacher(firstName, lastName, id);
                    users.add(user);
                } else if (accountType == Enums.AccountType.STUDENT) {
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

        String query = null;

        Statement statement;
        ResultSet resultSet;
        
        String type = null;

        if (user.getAccountType() == Enums.AccountType.TEACHER) {
            query = "SELECT TEACHER FROM " + DATABASE_NAME + ".RULES WHERE RULE=maxDays";
            type = "TEACHER";
        } else if (user.getAccountType() == Enums.AccountType.STUDENT) {
            query = "SELECT STUDENT FROM " + DATABASE_NAME + ".RULES WHERE RULE=maxDays";
            type = "STUDENT";
        }

        days = getMaxRule(query, type);
        return days;
    }

    public int getMaxBooks(User user) {
        int books = 0;

        String query = null;

        Statement statement;
        ResultSet resultSet;

        String type = null;

        if (user.getAccountType() == Enums.AccountType.TEACHER) {
            query = "SELECT TEACHER FROM " + DATABASE_NAME + ".RULES WHERE RULE=maxBooks";
            type = "TEACHER";
        } else if (user.getAccountType() == Enums.AccountType.STUDENT) {
            query = "SELECT STUDENT FROM " + DATABASE_NAME + ".RULES WHERE RULE=maxBooks";
            type = "STUDENT";
        }

        books = getMaxRule(query, type);
        return books;
    }

    private int getMaxRule(String query, String type) {
        Statement statement;
        ResultSet resultSet;
        int max = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            max = (int) resultSet.getDouble(type);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return max;
    }
}
