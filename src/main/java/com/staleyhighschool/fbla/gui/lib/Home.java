package com.staleyhighschool.fbla.gui.lib;

import com.staleyhighschool.fbla.gui.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Home {

    private Scene home;
    private VBox layout;

    private Button mBooks;
    private Button mUsers;
    private Button mLogs;

    public Home() {
        layout = new VBox(20);
        generateButtons();
        home = new Scene(layout, 960, 540);
    }

    public Scene getHome() {
        return home;
    }

    private void generateButtons() {
        mBooks = new Button("Manage Books");
        mUsers = new Button("Manage Users");
        mLogs = new Button("Manage Logs");

        layout.getChildren().addAll(mBooks, mUsers, mLogs);

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
