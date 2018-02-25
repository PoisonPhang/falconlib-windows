package com.staleyhighschool.fbla.gui.lib;

import com.staleyhighschool.fbla.gui.Main;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Home {

    private Scene home;
    private BorderPane layout;

    private final String name = " | Home";

    public Home() {
        layout = new BorderPane();
        layout.setLeft(Main.generateNavigation());
        home = new Scene(layout, 960, 540);
    }

    public Scene getHome() {
        return home;
    }

    public String getName() {
        return name;
    }
}
