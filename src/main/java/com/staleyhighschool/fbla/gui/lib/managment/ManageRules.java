package com.staleyhighschool.fbla.gui.lib.managment;

import com.staleyhighschool.fbla.gui.Main;
import com.staleyhighschool.fbla.library.Library;
import com.staleyhighschool.fbla.util.Enums;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ManageRules {

    Scene scene;
    BorderPane layout;
    GridPane ruleTable;

    public ManageRules() {
        layout = new BorderPane();
        layout.setLeft(Main.generateNavigation());
        scene = new Scene(layout, 960, 540);
    }

    public Scene getScene() {
        return scene;
    }

    GridPane generateGridPane() {
        GridPane pane = new GridPane();

        int tRow = 0;

        Text rule = new Text("Rule");
        Text teacher = new Text("Teacher");
        Text student = new Text("Student");

        pane.add(rule, 0, tRow);
        pane.add(teacher, 1, tRow);
        pane.add(student, 3, tRow);

        Text maxBooksTag = new Text("Max Books");
        Text maxDaysTag = new Text("Max Days");
        Text fineRateTag = new Text("Fine Rate");

        Text tMaxBooks = new Text(Double.toString(Library.connection.getRule(Enums.AccountType.TEACHER, "maxBooks")));
        Text tMaxDays = new Text(Double.toString(Library.connection.getRule(Enums.AccountType.TEACHER, "maxDays")));
        Text tFineRate = new Text(Double.toString(Library.connection.getRule(Enums.AccountType.TEACHER, "fineRate")));

        Text sMaxBooks = new Text(Double.toString(Library.connection.getRule(Enums.AccountType.STUDENT, "maxBooks")));
        Text sMaxDays = new Text(Double.toString(Library.connection.getRule(Enums.AccountType.STUDENT, "maxDays")));
        Text sFineRate = new Text(Double.toString(Library.connection.getRule(Enums.AccountType.STUDENT, "fineRate")));

        Button changeMaxBooks = new Button("Change Rule");
        Button changeMaxDays = new Button("Change Rule");
        Button changeFineRate = new Button("Change Rule");

        pane.add(maxBooksTag, 0, 1);
        pane.add(tMaxBooks, 1, 1);
        pane.add(sMaxBooks, 2, 1);
        pane.add(changeMaxBooks, 3, 1);

        pane.add(maxDaysTag, 0, 2);
        pane.add(tMaxDays, 1, 2);
        pane.add(sMaxDays, 2, 2);
        pane.add(changeMaxDays, 3, 2);

        pane.add(fineRateTag, 0, 3);
        pane.add(tFineRate, 1, 3);
        pane.add(sFineRate, 2, 3);
        pane.add(changeFineRate, 3, 3);

        return pane;
    }
}
