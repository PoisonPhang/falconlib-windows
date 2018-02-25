package com.staleyhighschool.fbla.gui.lib.books;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class ManageBooks {

    private Scene manageBooks;
    private StackPane layout;

    private final String name = " | Manage Books";

    public ManageBooks() {
        layout = new StackPane();
        manageBooks = new Scene(layout, 960, 540);
    }

    public Scene getManageBooks() {
        return manageBooks;
    }

    public String getName() {
        return name;
    }
}
