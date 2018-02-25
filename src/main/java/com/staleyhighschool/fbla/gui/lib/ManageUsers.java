package com.staleyhighschool.fbla.gui.lib;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class ManageUsers {

    private Scene manageUsers;
    private StackPane layout;

    public ManageUsers() {
        layout = new StackPane();
        manageUsers = new Scene(layout, 960, 540);
    }

    public Scene getManageUsers() {
        return manageUsers;
    }
}
