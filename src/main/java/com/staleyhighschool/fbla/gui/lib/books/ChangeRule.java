package com.staleyhighschool.fbla.gui.lib.books;

import com.staleyhighschool.fbla.gui.Main;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class ChangeRule {

    private Scene logs;
    BorderPane layout;

    private String ruleName;

    public ChangeRule(String ruleName) {
        this.ruleName = ruleName;
        layout = new BorderPane();
        layout.setLeft(Main.generateNavigation());
        logs = new Scene(layout, 960, 540);
    }

    public Scene getLogs() {
        return logs;
    }
}
