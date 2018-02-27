package com.staleyhighschool.fbla.gui;

import com.staleyhighschool.fbla.gui.lib.Home;
import com.staleyhighschool.fbla.gui.lib.books.ManageBooks;
import com.staleyhighschool.fbla.gui.lib.logging.Logs;
import com.staleyhighschool.fbla.gui.lib.users.ManageUsers;
import com.staleyhighschool.fbla.library.Library;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String APP_TITLE = "Falcon Library";

    public static Stage window;

    public static Library library;

    public static Home home;
    public static Logs logs;
    public static ManageBooks manageBooks;
    public static ManageUsers manageUsers;

    public static void main(String[] args) {
        library = new Library();
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

    public static VBox generateNavigation() {
        Button mHome = new Button("Home");
        Button mBooks = new Button("Manage Books");
        Button mUsers = new Button("Manage Users");
        Button mLogs = new Button("Logs");

        VBox navigation = new VBox();

        navigation.getChildren().addAll(mHome, mBooks, mUsers, mLogs);

        mHome.setOnAction(e -> {
            Main.window.setScene(home.getHome());
            Main.window.setTitle(APP_TITLE + home.getName());
        });
        mBooks.setOnAction(e -> {
            Main.window.setScene(manageBooks.getManageBooks());
            Main.window.setTitle(APP_TITLE + Main.manageBooks.getName());
        });
        mUsers.setOnAction(e -> {
            Main.window.setScene(manageUsers.getManageUsers());
            Main.window.setTitle(APP_TITLE + Main.manageUsers.getName());
        });
        mLogs.setOnAction(e -> {
            Main.window.setScene(logs.getLogs());
            Main.window.setTitle(APP_TITLE + Main.logs.getName());
        });

        return navigation;
    }
}
