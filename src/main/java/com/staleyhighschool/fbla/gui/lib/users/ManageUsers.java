package com.staleyhighschool.fbla.gui.lib.users;

import com.staleyhighschool.fbla.gui.Main;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class ManageUsers {

    private Scene manageUsers;
    private BorderPane layout;

    private final String name = " | Manage Users";

    public ManageUsers() {
        layout = new BorderPane();
        layout.setLeft(Main.generateNavigation());
        manageUsers = new Scene(layout, 960, 540);
    }

    public Scene getManageUsers() {
        return manageUsers;
    }

    public String getName() {
        return name;
    }
}
