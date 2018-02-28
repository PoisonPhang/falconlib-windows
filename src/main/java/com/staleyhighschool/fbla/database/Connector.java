package com.staleyhighschool.fbla.database;

import com.staleyhighschool.fbla.library.Book;
import com.staleyhighschool.fbla.library.Library;
import com.staleyhighschool.fbla.users.Student;
import com.staleyhighschool.fbla.users.Teacher;
import com.staleyhighschool.fbla.users.User;
import com.staleyhighschool.fbla.util.Enums;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.*;
import java.util.ArrayList;
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
    private final String DATABASE_URL = "jdbc:mysql://192.168.1.116:3306/FalconLib"; // TODO change before submission
    private final String PORT = ":3306";

    /**
     * Establishes connection to database
     */
    public Connector() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, "FalconC", "mypass");
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

        String query = "SELECT title, author, id, isOut, isLate, dateOut " + "FROM LibraryBooks";

        Statement statement;
        ResultSet resultSet;

        String bookTitle;
        String bookAuthor;
        String bookID;
        Enums.IsLate isLate;
        Enums.IsOut isOut;
        Date dateOut;

        List<Book> books = new ArrayList<>();
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

    /**
     * Gets list of books that the given {@link User} has
     *
     * @param user {@link User}
     * @return {@link List<Book>}
     */
    public List<Book> getUserBooks(User user) {

        List<Book> userBooks = new ArrayList<>();
        Book book;

        String bookTitle;
        String bookAuthor;
        String bookID;
        Enums.IsLate isLate = null;
        Enums.IsOut isOut = null;

        String query = "SELECT books " + "FROM `" + user.getUserID() + "`";

        Statement statement;
        ResultSet resultSet;

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                for (int i = 0; i < Library.bookList.size(); i++) {
                    if (resultSet.getString("books").equals(Library.bookList.get(i).getBookID())) {
                        userBooks.add(Library.bookList.get(i));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userBooks;
    }

    public double getFineRate(Enums.AccountType accountType) {

        Statement statement;
        ResultSet resultSet;

        String query;
        double fineRate = 0;

        query = "SELECT student, teacher FROM Rules";

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
                "VALUES ('" + user.getFirstName() +
                "', '" + user.getLastName() +
                "', '" + user.getUserID() +
                "', '" + accountType + "')";

        String createTableQuery = "CREATE TABLE `" + user.getUserID() + "` (books TEXT)";

        String insertBlank = "INSERT INTO `" + user.getUserID() + "` (books) VALUES ('space')";

        try {

            statement = connection.createStatement();

            statement.executeUpdate(addToUsersQuery);
            statement.executeUpdate(createTableQuery);
            statement.executeUpdate(insertBlank);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(User user) {

        String deleteUserTable = "DROP `" + user.getUserID() + "`";
        String deleteUserFromLibrary = "DELETE FROM Users WHERE id='" + user.getUserID() + "'";
        Library.userList.remove(user);

        Statement statement;

        if (user.getUserBooks().size() -2 == 0) {
            try {
                statement = connection.createStatement();

                statement.executeUpdate(deleteUserTable);
                statement.executeUpdate(deleteUserFromLibrary);
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
                "VALUES ('" + title +
                "', '" + author +
                "', '" + id +
                "', " + isOut +
                ", " + isLate +
                ", '2000-01-01')";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteBook(Book book) {
        System.out.println(TAG + "Book ID: " + book.getBookID());
        String query = "DELETE FROM LibraryBooks WHERE id='" + book.getBookID() + "'";

        Statement statement;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            System.out.println(TAG + "Books been Yeeted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getCurrentUsers() {

        List<User> users = new ArrayList<>();

        User user;

        String firstName, lastName, id;
        Enums.AccountType accountType = null;

        Statement statement;
        ResultSet resultSet;

        String query = "SELECT firstName, lastName, id, accountType FROM Users";

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
                    user.setUserBooks();
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
            query = "SELECT teacher FROM Rules WHERE rule='maxDays'";
            type = "teacher";
        } else if (user.getAccountType() == Enums.AccountType.STUDENT) {
            query = "SELECT student FROM Rules WHERE rule='maxDays'";
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
            query = "SELECT teacher FROM Rules WHERE rule='maxBooks'";
            type = "teacher";
        } else if (user.getAccountType() == Enums.AccountType.STUDENT) {
            query = "SELECT student FROM Rules WHERE rule='maxBooks'";
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

            while (resultSet.next()) {
                max = (int) resultSet.getDouble(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return max;
    }

    public boolean userCheckOut(User user, Book book) {
        String query;

        Statement statement;

        System.out.println(TAG + "User book count:" + user.getUserBooks().size() + " " + getMaxBooks(user));

        if (user.getUserBooks().size() + 1 <= getMaxBooks(user)) {
            System.out.println(TAG + "good yyet");
            query = "INSERT INTO `" + user.getUserID() + "` (books) VALUES ('" + book.getBookID() + "')";
            String setOut = "UPDATE LibraryBooks SET isOut=TRUE WHERE id='" + book.getBookID() + "'";
            book.setIsOut(Enums.IsOut.OUT);

            try {
                statement = connection.createStatement();
                statement.executeUpdate(query);
                statement.executeUpdate(setOut);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void userReturnBook(Book book) {
        String query;

        Statement statement;
        ResultSet resultSet;

        boolean pass = false;
        System.out.println(TAG + " Got in");
        do {
            for (User user : Library.userList) {
                System.out.println(TAG + "Cycling users");
                try {
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("SELECT books FROM `" + user.getUserID() + "`");

                    while (resultSet.next()) {
                        if (book.getBookID().equals(resultSet.getString("books"))) {
                            query = "DELETE FROM `" + user.getUserID() + "` WHERE books='" + book.getBookID() + "'";
                            String setIn = "UPDATE LibraryBooks SET isOut=FALSE WHERE id='" + book.getBookID() + "'";
                            book.setIsOut(Enums.IsOut.IN);
                            try {
                                statement = connection.createStatement();
                                statement.executeUpdate(query);
                                statement.executeUpdate(setIn);

                                pass = true;
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } while (!pass);
    }

    public double getRule(Enums.AccountType accountType, String rule) {
        String query = null;

        Statement statement;
        ResultSet resultSet;

        String type = null;

        double rRule = 0;

        if (accountType == Enums.AccountType.TEACHER) {
            type = "teacher";
            query = "SELECT teacher FROM Rules WHERE rule='" + rule + "'";
        } else if (accountType == Enums.AccountType.STUDENT) {
            type = "student";
            query = "SELECT student FROM Rules WHERE rule='" + rule + "'";
        }

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            rRule = resultSet.getDouble(type);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rRule;
    }

    public void setRule(Enums.AccountType accountType, String rule, double value) {
        String query =  null;
        String type = null;

        Statement statement;

        if (accountType == Enums.AccountType.TEACHER) {
            type = "teacher";
        } else if (accountType == Enums.AccountType.STUDENT) {
            type = "student";
        }

        query = "UPDATE Rules SET " + type + "=" + value + " where rule='" + rule + "'";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkValidID(String string) {
        String query = null;

        Statement statement;
        ResultSet resultSet;

        boolean pass =  true;

        try {
            query = "SELECT id FROM Users";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if (string.equals(resultSet.getString("id"))) {
                    pass = false;
                }
            }

            query = "SELECT id FROM LibraryBooks";

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                if (string.equals(resultSet.getString("id"))) {
                    pass = false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pass;
    }
}
