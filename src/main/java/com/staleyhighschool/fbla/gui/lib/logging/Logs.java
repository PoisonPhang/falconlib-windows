package com.staleyhighschool.fbla.gui.lib.logging;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

public class Logs {

    private Scene logs;
    StackPane layout;

    private final String name = " | Logs";

    public Logs() {
        layout = new StackPane();
        logs = new Scene(layout, 960, 540);
    }

    public Scene getLogs() {
        return logs;
    }

    public String getName() {
        return name;
    }
}
