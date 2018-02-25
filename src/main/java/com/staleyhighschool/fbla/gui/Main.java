package com.staleyhighschool.fbla.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    private final String APP_TITLE = "Falcon Library";

    private Scene home;
    private Scene manageUsers;
    private Scene manageBooks;
    private Scene logs;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(APP_TITLE);
    }
}
