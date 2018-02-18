package com.staleyhighschool.fbla.Database;

import com.staleyhighschool.fbla.Users.User;
import com.staleyhighschool.fbla.util.Enums;

import java.sql.*;
import java.util.List;

/**
 * Establishes connection to Book database
 *
 * @author PoisonPhang
 */
public class Connector {

    public static Connection connection;

    private final String DATABASE_NAME = "";
    private final String DATABASE_URL = "";
    private final String PORT = ":";

    public Connector() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL + PORT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public List<Book> getLibraryBooks() {

        String query = "select TITLE, AUTHOR, ID, IS_OUT, IS_LATE " + "from " + DATABASE_NAME + ".LIBRARY_BOOKS";

        return setBooks(query);
    }

    public List<Book> getUserBooks(User user) {

        String query = "select TITLE, AUTHOR, ID, IS_OUT, IS_LATE " + "from " + DATABASE_NAME + "." + user.getUserID();

        return setBooks(query);
    }

    private List<Book> setBooks(String query) {
        Statement statement;
        ResultSet resultSet;

        String bookTitle;
        String bookAuthor;
        String bookID;
        Enums.IsLate isLate = null;
        Enums.IsOut isOut = null;

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

                book = new Book(bookTitle, bookAuthor, bookID, isLate, isOut);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
