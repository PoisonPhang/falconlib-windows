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

    private final String DATABASE_NAME = "FalconLib";
    private final String DATABASE_URL = "jdbc:mysql://192.168.1.116:3306/FalconLib";
    private final String PORT = ":3306";

    /**
     * Establishes connection to database
     */
    public Connector() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, "Falcon", "mypass");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the existing connection
     *
     * @return existing {@link Connection}
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Gets list of all books in the library
     *
     * @return {@link List<Book>}
     */
    public List<Book> getLibraryBooks() {

        String query = "select title, author, id, isOut, isLate, dateOut " + "from " + DATABASE_NAME + ".LibraryBooks";

        return setBooks(query);
    }

    /**
     * Gets list of books that the given {@link User} has
     *
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

        String query = "SELECT id " + "FROM " + DATABASE_NAME + "." + user.getUserID();

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
     *
     * @param query {@link String}
     * @return {@link List<Book>}
     */
    private List<Book> setBooks(String query) {

        Statement statement;
        ResultSet resultSet;

        String bookTitle;
        String bookAuthor;
        String bookID;
        Enums.IsLate isLate;
        Enums.IsOut isOut;
        Date dateOut;

        List<Book> books = null;
        Book book;

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                if (resultSet.getBoolean("isLate")) {
                    isLate = Enums.IsLate.LATE;
                } else {
                    isLate = Enums.IsLate.SAFE;
                }
                if (resultSet.getBoolean("isOut")) {
                    isOut = Enums.IsOut.OUT;
                } else {
                    isOut = Enums.IsOut.IN;
                }

                bookTitle = resultSet.getString("title");
                bookAuthor = resultSet.getString("author");
                bookID = resultSet.getString("id");
                dateOut = resultSet.getDate("dateOut");

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

        query = "SELECT student, teacher FROM " + DATABASE_NAME + ".Rules";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if (accountType == Enums.AccountType.TEACHER) {
                    fineRate = resultSet.getDouble("teacher");
                } else if (accountType == Enums.AccountType.STUDENT) {
                    fineRate = resultSet.getDouble("teacher");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fineRate;
    }

    public void addUser(User user) {
        String accountType = null;

        Statement statement;

        if (user.getAccountType() == Enums.AccountType.TEACHER) {
            accountType = "teacher";
        } else if (user.getAccountType() == Enums.AccountType.STUDENT) {
            accountType = "student";
        }

        String addToUsersQuery = "INSERT INTO Users (firstName, lastName, id, accountType) " +
                "VALUES (" + user.getFirstName() +
                ", " + user.getLastName() +
                ", " + user.getUserID() +
                ", " + accountType + ")";

        String createTableQuery = "CREATE TABLE " + user.getUserID() + " (Books STRING)";

        try {

            statement = connection.createStatement();

            statement.executeUpdate(addToUsersQuery);
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBook(Book book) {

        String title = book.getBookTitle();
        String author = book.getBookAuthor();
        String id = book.getBookID();
        boolean isOut = book.isOut();
        boolean isLate = book.isLate();
        Date dateOut = Book.storeDate;

        Statement statement;

        String query = "INSERT INTO LibraryBooks (title, author, id, isOut, isLate, dateOut) " +
                "VALUES (" + title +
                ", " + author +
                ", " + id +
                ", " + isOut +
                ", " + isLate +
                ", " + dateOut + ")";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(Book book) {
        String query = "DELETE FROM LibraryBooks WHERE id=" + book.getBookID();

        Statement statement;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getCurrentUsers() {

        List<User> users = null;

        User user;

        String firstName, lastName, id;
        Enums.AccountType accountType = null;

        Statement statement;
        ResultSet resultSet;

        String query = "SELECT firstName, lastName, id, accountType FROM " + DATABASE_NAME + ".Users";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                if (resultSet.getString("accountType").equals("teacher")) {
                    accountType = Enums.AccountType.TEACHER;
                } else if (resultSet.getString("accountType").equals("student")) {
                    accountType = Enums.AccountType.STUDENT;
                }

                firstName = resultSet.getString("firstName");
                lastName = resultSet.getString("lastName");
                id = resultSet.getString("id");

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
        int days;

        String query = null;
        String type = null;

        if (user.getAccountType() == Enums.AccountType.TEACHER) {
            query = "SELECT teacher FROM " + DATABASE_NAME + ".Rules WHERE rule=maxDays";
            type = "teacher";
        } else if (user.getAccountType() == Enums.AccountType.STUDENT) {
            query = "SELECT student FROM " + DATABASE_NAME + ".Rules WHERE rule=maxDays";
            type = "teacher";
        }

        days = getMaxRule(query, type);
        return days;
    }

    public int getMaxBooks(User user) {
        int books;

        String query = null;
        String type = null;

        if (user.getAccountType() == Enums.AccountType.TEACHER) {
            query = "SELECT teacher FROM " + DATABASE_NAME + ".Rules WHERE rule=maxBooks";
            type = "teacher";
        } else if (user.getAccountType() == Enums.AccountType.STUDENT) {
            query = "SELECT student FROM " + DATABASE_NAME + ".Rules WHERE rule=maxBooks";
            type = "student";
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

    public void userCheckOut(User user, Book book) {
        String query;

        Statement statement;

        query = "INSERT INTO " + user.getUserID() + "(books) VALUES (" + book.getBookID() + ")";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void userReturnBook(User user, Book book) {
        String query;

        Statement statement;

        query = "DELETE FROM " + user.getUserID() + " WHERE books=" + book.getBookID();

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
