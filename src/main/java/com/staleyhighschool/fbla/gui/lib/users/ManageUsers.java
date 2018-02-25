package com.staleyhighschool.fbla.gui.lib.users;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class ManageUsers {

    private Scene manageUsers;
    private StackPane layout;
    private final String name = " | Manage Users";

    public ManageUsers() {
        layout = new StackPane();
        manageUsers = new Scene(layout, 960, 540);
    }

    public Scene getManageUsers() {
        return manageUsers;
    }

    public String getName() {
        return name;
    }
}
