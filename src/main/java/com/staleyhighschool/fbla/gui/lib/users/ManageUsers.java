package com.staleyhighschool.fbla.gui.lib.users;

import com.staleyhighschool.fbla.gui.Main;
import com.staleyhighschool.fbla.library.Book;
import com.staleyhighschool.fbla.library.Library;
import com.staleyhighschool.fbla.users.User;
import com.staleyhighschool.fbla.util.Enums;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ManageUsers {

    private Scene manageUsers;
    private BorderPane layout;

    private int totalRows;

    private List<CheckBox> checkBoxes;
    private List<User> selectedUsers;

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

    private GridPane populateUserList() {
        totalRows = 0;
        GridPane pane = new GridPane();

        pane.setPadding(new Insets(4));
        pane.setVgap(4);
        pane.setHgap(8);

        checkBoxes = new ArrayList<>();
        selectedUsers = new ArrayList<>();

        int wRow = 1;
        int tRow = 0;

        Text uIDTag = new Text("ID");
        Text fNameTag = new Text("First Name");
        Text lNameTag = new Text("Last Name");
        Text aTypeTag = new Text("Account Type");

        pane.add(uIDTag, 1, tRow);
        pane.add(fNameTag, 2, tRow);
        pane.add(lNameTag, 3, tRow);
        pane.add(aTypeTag, 4, tRow);

        for (User user : Library.userList) {

            String type = null;

            if (user.getAccountType() == Enums.AccountType.TEACHER) {
                type = "teacher";
            } else if (user.getAccountType() == Enums.AccountType.STUDENT) {
                type = "student";
            }

            CheckBox uID = new CheckBox(user.getUserID());
            Text fName = new Text(user.getFirstName());
            Text lName = new Text(user.getLastName());
            Text aType = new Text(type);

            checkBoxes.add(uID);

            pane.add(uID, 1, wRow);
            pane.add(fName, 2, wRow);
            pane.add(lName, 3, wRow);
            pane.add(aType, 4, wRow);

            wRow++;
            totalRows = wRow;
        }

        return pane;
    }
}
