package com.staleyhighschool.fbla.gui.lib.books;

import com.staleyhighschool.fbla.gui.Main;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class ManageBooks {

    private Scene manageBooks;
    private BorderPane layout;

    private final String name = " | Manage Books";

    public ManageBooks() {
        layout = new BorderPane();
        layout.setLeft(Main.generateNavigation());
        manageBooks = new Scene(layout, 960, 540);
    }

    public Scene getManageBooks() {
        return manageBooks;
    }

    public String getName() {
        return name;
    }
}
