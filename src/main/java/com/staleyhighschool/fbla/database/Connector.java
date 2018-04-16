package com.staleyhighschool.fbla.database;

import com.staleyhighschool.fbla.library.Book;
import com.staleyhighschool.fbla.library.Library;
import com.staleyhighschool.fbla.users.Student;
import com.staleyhighschool.fbla.users.Teacher;
import com.staleyhighschool.fbla.users.User;
import com.staleyhighschool.fbla.util.Enums;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Establishes connection with the database, gets values from database, changes values in database,
 * and adds values to database.
 */
public class Connector {

  private String TAG = (this.getClass().getName() + ": ");

  public static Connection connection;

  private final String DATABASE_NAME = "sql3223801";
  private final String DATABASE_URL = "jdbc:sqlite:falcon-lib.db";
  private final String PORT = ":3306";
  public static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  /**
   * Establishes connection to database
   */
  public Connector() {
    try {
      connection = DriverManager.getConnection(DATABASE_URL);
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

    Library.logging.writeToLog(Enums.LogType.USER_ACTION, "New User: " + user.getUserID());
  }

  public void editUser(User user, String column, String value) {
    String query =
        "UPDATE Users SET " + column + "='" + value + "' WHERE id='" + user.getUserID() + "'";

    if (column.equals("firstName")) {
      user.setFirstName(value);
    } else if (column.equals("lastName")) {
      user.setLastName(value);
    }

    Statement statement;

    try {
      statement = connection.createStatement();

      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Library.logging.writeToLog(Enums.LogType.USER_ACTION, "Edit made to User: " + user.getUserID());
  }

  public void deleteUser(User user) {

    String deleteUserTable = "DROP TABLE `" + user.getUserID() + "`";
    String deleteUserFromLibrary = "DELETE FROM Users WHERE id='" + user.getUserID() + "'";

    Statement statement;

    System.out.println(TAG + "User " + (user.getUserBooks().size() - 2));
    if (user.getUserBooks().size() == 0) {
      Library.logging.writeToLog(Enums.LogType.USER_ACTION, "Deleted User: " + user.getUserID());
      Library.userList.remove(user);
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
        ", '" + dateFormat.format(Book.storeDate) + "')";

    try {
      statement = connection.createStatement();
      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Library.logging.writeToLog(Enums.LogType.BOOK_ACTION, "Created Book: " + book.getBookID());
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

    Library.logging.writeToLog(Enums.LogType.BOOK_ACTION, "Deleted Book: " + book.getBookID());
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

    Library.logging.writeToLog(Enums.LogType.CHECKOUT,
        "User: " + user.getUserID() + ", checked out Book: " + book.getBookID());

    if (user.getUserBooks().size() + 1 <= getMaxBooks(user)) {
      System.out.println(TAG + "good yyet");
      query = "INSERT INTO `" + user.getUserID() + "` (books) VALUES ('" + book.getBookID() + "')";
      String setOut = "UPDATE LibraryBooks SET isOut=TRUE WHERE id='" + book.getBookID() + "'";
      String setDate =
          "UPDATE LibraryBooks SET dateOut='" + dateFormat.format(Calendar.getInstance().getTime())
              + "' WHERE id='" + book.getBookID() + "'";
      book.setIsOut(Enums.IsOut.OUT);
      try {
        book.setDateOut(
            DateUtils.parseDate(dateFormat.format(Calendar.getInstance().getTime()), "yyyy-MM-dd"));
      } catch (ParseException e) {
        e.printStackTrace();
      }
      System.out.println(TAG + dateFormat.format(Calendar.getInstance().getTime()));

      try {
        statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.executeUpdate(setOut);
        statement.executeUpdate(setDate);
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

              Library.logging.writeToLog(Enums.LogType.RETURN,
                  "User: " + user.getUserID() + ", returned Book: " + book.getBookID());
              query =
                  "DELETE FROM `" + user.getUserID() + "` WHERE books='" + book.getBookID() + "'";
              String setIn =
                  "UPDATE LibraryBooks SET isOut=FALSE WHERE id='" + book.getBookID() + "'";
              String setDate =
                  "UPDATE LibraryBooks SET dateOut='" + dateFormat.format(Book.storeDate)
                      + "' WHERE id='" + book.getBookID() + "'";
              book.setIsOut(Enums.IsOut.IN);
              book.setIsLate(Enums.IsLate.SAFE);
              book.setDateOut(Book.storeDate);
              String setIsLate =
                  "UPDATE LibraryBooks SET isLate=FALSE WHERE id='" + book.getBookID() + "'";
              try {
                statement = connection.createStatement();
                statement.executeUpdate(query);
                statement.executeUpdate(setIn);
                statement.executeUpdate(setDate);
                statement.executeUpdate(setIsLate);

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

      while (resultSet.next()) {
        rRule = resultSet.getDouble(type);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return rRule;
  }

  public void setRule(Enums.AccountType accountType, String rule, double value) {
    String query = null;
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

    boolean pass = true;

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

  public String getLastLogDate() {
    String query = "SELECT LastLogDate FROM LogDate";

    Statement statement;
    ResultSet resultSet;

    try {
      statement = connection.createStatement();
      resultSet = statement.executeQuery(query);

      while (resultSet.next()) {
        return dateFormat.format(resultSet.getDate("LastLogDate"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  public void setLastLogDate() {
    String query =
        "UPDATE LogDate SET LastLogDate='" + dateFormat.format(Calendar.getInstance().getTime())
            + "'";

    Statement statement;

    try {
      statement = connection.createStatement();
      statement.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean checkLogDate() {
    String query = "SELECT LastLogDate FROM LogDate";
    Date today = null;
    Date lastLog = null;

    Statement statement;
    ResultSet resultSet;

    try {
      today = DateUtils
          .parseDate(dateFormat.format(Calendar.getInstance().getTime()), "yyyy-MM-dd");
    } catch (ParseException e) {
      e.printStackTrace();
    }

    try {
      statement = connection.createStatement();
      resultSet = statement.executeQuery(query);

      while (resultSet.next()) {
        lastLog = (resultSet.getDate("LastLogDate"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (DateUtils.addWeeks(lastLog, 1).before(today)) {
      return true;
    }

    return false;
  }
}
