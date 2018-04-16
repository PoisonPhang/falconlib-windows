package com.staleyhighschool.fbla.library;

import com.staleyhighschool.fbla.util.enums.LogType;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logging {

  private final String TAG = (this.getClass().getName() + ": ");

  private String userName;
  private String logSaveDirPath;
  File logSaveDirectory;
  FileWriter log;
  DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
  DateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
  BufferedWriter logWritter;
  PrintWriter writer;

  public Logging() {
    userName = System.getProperty("user.name");
    logSaveDirPath = "C:\\Users\\" + userName + "\\Documents\\falcon-lib-logs";
    createDirectory();
    log = checkLogCreation();
    logWritter = new BufferedWriter(log);
    writer = new PrintWriter(logWritter);
  }

  private void createDirectory() {

    logSaveDirectory = new File(logSaveDirPath);

    if (!logSaveDirectory.exists()) {
      boolean pass = logSaveDirectory.mkdirs();

      if (!pass) {
        System.out.println("Failed to create directory " + logSaveDirPath);
      }
    }
  }

  public void writeToLog(LogType logType, String message) {
    if (logType == LogType.USER_ACTION) {
      writer.println(
          dateTime.format(Calendar.getInstance().getTime()) + " [ USER EVENT ]: " + message);
      System.out.println(
          TAG + dateTime.format(Calendar.getInstance().getTime()) + " [ USER EVENT ]: " + message);
    } else if (logType == LogType.BOOK_ACTION) {
      writer.println(
          dateTime.format(Calendar.getInstance().getTime()) + " [ BOOK EVENT ]: " + message);
      System.out.println(
          TAG + dateTime.format(Calendar.getInstance().getTime()) + " [ BOOK EVENT ]: " + message);
    } else if (logType == LogType.CHECKOUT) {
      writer.println(
          dateTime.format(Calendar.getInstance().getTime()) + " [ BOOK CHECKED OUT ]: " + message);
      System.out.println(
          TAG + dateTime.format(Calendar.getInstance().getTime()) + " [ BOOK CHECKED OUT ]: "
              + message);
    } else if (logType == LogType.RETURN) {
      writer.println(
          dateTime.format(Calendar.getInstance().getTime()) + " [ BOOK RETURNED ]: " + message);
      System.out.println(
          TAG + dateTime.format(Calendar.getInstance().getTime()) + " [ BOOK RETURNED ]: "
              + message);
    }
  }

  public void closeLog() {
    System.out.println(TAG + "closing");
    writer.close();
  }

  private FileWriter checkLogCreation() {
    if (Library.connection.checkLogDate()) {
      Library.connection.setLastLogDate();
      try {
        return new FileWriter(
            ((new File(logSaveDirPath, format.format(Calendar.getInstance().getTime())))), true);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      try {
        return new FileWriter(new File(logSaveDirPath, Library.connection.getLastLogDate()), true);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
}
