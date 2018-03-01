package com.staleyhighschool.fbla.gui.lib.users;

import com.staleyhighschool.fbla.gui.Main;
import com.staleyhighschool.fbla.library.Library;
import com.staleyhighschool.fbla.users.User;
import com.staleyhighschool.fbla.util.Enums;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.*;

public class ManageUsers {

    private String TAG = (this.getClass().getName() + ": ");

    private Scene manageUsers;
    private BorderPane layout;
    private GridPane userList;
    private ScrollPane mainContent;
    private HBox topMenu;

    private int totalRows;

    private List<CheckBox> checkBoxes;
    private List<User> selectedUsers;
    private List<Integer> indexOfSelected;

    private final String name = " | Manage Users";

    public ManageUsers() {
        userList = populateUserList();
        topMenu = generateTopMenu();
        mainContent = new ScrollPane();
        mainContent.setContent(userList);

        layout = new BorderPane();
        layout.setLeft(Main.generateNavigation());
        layout.setCenter(mainContent);
        layout.setTop(topMenu);
        manageUsers = new Scene(layout, 960, 540);
    }

    public Scene getManageUsers() {
        return manageUsers;
    }

    public String getName() {
        return name;
    }

    public void refresh() {
        userList = populateUserList();
        mainContent.setContent(userList);
    }

    private HBox generateTopMenu() {
        HBox box = new HBox();

        Button addUser = new Button("Add User");
        Button deleteSelceted = new Button("Delete Selected");
        Button refresh = new Button("Refresh");

        addUser.setOnAction(e -> Main.changeScene(new AddUser().getAddUser()));
        deleteSelceted.setOnAction(e -> {
            checkSelected();
            Collections.sort(indexOfSelected);
            int runs = 0;
            for (int row : indexOfSelected) {
                checkBoxes.remove(row - runs);
                deleteRow(userList, row - runs + 1);
                runs++;
            }
            Main.library.deleteUsers(selectedUsers);
            deselectAll();
            refresh();
        });

        box.getChildren().addAll(addUser, deleteSelceted, refresh);

        return box;
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

            Button changeUserInfo = new Button("Change Info");

            changeUserInfo.setOnAction(event -> Main.changeScene(new ChangeUserInfo(user).getScene()));

            checkBoxes.add(uID);

            pane.add(uID, 1, wRow);
            pane.add(fName, 2, wRow);
            pane.add(lName, 3, wRow);
            pane.add(aType, 4, wRow);
            pane.add(changeUserInfo, 5, wRow);

            wRow++;
            totalRows = wRow;
        }

        return pane;
    }

    public void appendList(User user) {

        userList.setPadding(new Insets(4));
        userList.setVgap(4);
        userList.setHgap(8);

        checkBoxes = new ArrayList<>();
        selectedUsers = new ArrayList<>();

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

        userList.add(uID, 1, totalRows);
        userList.add(fName, 2, totalRows);
        userList.add(lName, 3, totalRows);
        userList.add(aType, 4, totalRows);
        totalRows++;
    }


    private void checkSelected() {
        indexOfSelected = new ArrayList<>();
        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                selectedUsers.add(Library.userList.get(i));
                indexOfSelected.add(i);
            }
        }
    }

    private void deselectAll() {
        for (CheckBox box : checkBoxes) {
            box.setSelected(false);
        }
    }

    private void deleteRow(GridPane grid, final int row) {
        Set<Node> deleteNodes = new HashSet<>();
        for (Node child : grid.getChildren()) {
            // get index from child
            Integer rowIndex = GridPane.getRowIndex(child);

            // handle null values for index=0
            int r = rowIndex == null ? 0 : rowIndex;

            if (r > row) {
                // decrement rows for rows after the deleted row
                GridPane.setRowIndex(child, r - 1);
            } else if (r == row) {
                // collect matching rows for deletion
                deleteNodes.add(child);
            }
        }

        // remove nodes from row
        grid.getChildren().removeAll(deleteNodes);
    }
}
