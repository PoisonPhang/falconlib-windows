package com.staleyhighschool.fbla.gui.lib.managment;

import com.staleyhighschool.fbla.gui.Main;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Logs {

    private Scene logs;
    BorderPane layout;

    public Logs() {
        layout = new BorderPane();
        layout.setLeft(Main.generateNavigation());
        logs = new Scene(layout, 960, 540);
    }

    public Scene getLogs() {
        return logs;
    }

}
