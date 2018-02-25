package com.staleyhighschool.fbla.gui;

import com.staleyhighschool.fbla.gui.lib.Home;
import com.staleyhighschool.fbla.gui.lib.logging.Logs;
import com.staleyhighschool.fbla.gui.lib.books.ManageBooks;
import com.staleyhighschool.fbla.gui.lib.users.ManageUsers;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application{

    public static final String APP_TITLE = "Falcon Library";

    public static Stage window;

    public static Home home;
    public static Logs logs;
    public static ManageBooks manageBooks;
    public static ManageUsers manageUsers;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;

        home = new Home();
        logs = new Logs();
        manageBooks = new ManageBooks();
        manageUsers = new ManageUsers();
        window.setTitle(APP_TITLE + " | Home");

        window.setScene(home.getHome());
        window.show();
    }

}
