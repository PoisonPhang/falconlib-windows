package com.staleyhighschool.fbla.gui.lib;

import com.staleyhighschool.fbla.gui.Main;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Home {

    private Scene home;
    private VBox navigation;
    private BorderPane layout;

    private Button mHome;
    private Button mBooks;
    private Button mUsers;
    private Button mLogs;

    public Home() {
        navigation = new VBox(10);
        layout = new BorderPane();
        layout.setLeft(navigation);
        generateNavigation();
        home = new Scene(layout, 960, 540);
    }

    public Scene getHome() {
        return home;
    }

    private void generateNavigation() {
        mHome = new Button("Home");
        mBooks = new Button("Manage Books");
        mUsers = new Button("Manage Users");
        mLogs = new Button("Logs");

        navigation.getChildren().addAll(mHome, mBooks, mUsers, mLogs);

        mHome.setOnAction(e -> {
            Main.window.setScene(home);
            Main.window.setTitle(Main.APP_TITLE + " | Home");
        });
        mBooks.setOnAction(e -> {
            Main.window.setScene(Main.manageBooks.getManageBooks());
            Main.window.setTitle(Main.APP_TITLE + " | Manage Books");
        });
        mUsers.setOnAction(e -> {
            Main.window.setScene(Main.manageUsers.getManageUsers());
            Main.window.setTitle(Main.APP_TITLE + " | Manage Users");
        });
        mLogs.setOnAction(e -> {
            Main.window.setScene(Main.logs.getLogs());
            Main.window.setTitle(Main.APP_TITLE + " | Logs");
        });
    }
}
