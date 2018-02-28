package com.staleyhighschool.fbla.gui.lib.books;

import com.staleyhighschool.fbla.gui.Main;
import com.staleyhighschool.fbla.gui.util.Alert;
import com.staleyhighschool.fbla.library.Book;
import com.staleyhighschool.fbla.library.Library;
import com.staleyhighschool.fbla.users.User;
import com.staleyhighschool.fbla.util.Enums;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class CheckOutBook {

    private int totalRows;
    private ToggleGroup radioButtons;

    private Scene checkOutBook;
    private BorderPane layout;
    private ScrollPane mainContent;
    private GridPane userList;
    private VBox checkMenu;

    private Book bookToCheck;

    public CheckOutBook(Book book) {
        bookToCheck = book;
        checkMenu = generateCheckMenu();

        userList = populateUserList();

        mainContent = new ScrollPane();
        mainContent.setContent(userList);

        layout = new BorderPane();
        layout.setLeft(Main.generateNavigation());
        layout.setCenter(mainContent);
        layout.setLeft(checkMenu);

        checkOutBook = new Scene(layout, 960, 540);
    }

    private void checkOut() {

        System.out.println(radioButtons.getSelectedToggle().toString());
//        for (User user : Library.userList) {
//            if (user.getUserID().equals(radioButtons.getSelectedToggle().toString())) {
//                Main.library.checkOutBook(user, bookToCheck);
//            }
//        }
    }

    public Scene getCheckOutBook() {
        return checkOutBook;
    }

    private VBox generateCheckMenu() {
        VBox box = new VBox();
        Text title = new Text("Book Title: " + bookToCheck.getBookTitle());
        Text bookID = new Text("Book ID: " + bookToCheck.getBookID());

        Button check = new Button("Check Out");

        check.setOnAction(e -> {
            checkOut();
            Alert.display("Checked Out", ("Book: " + bookToCheck.getBookTitle() + "(" + bookToCheck.getBookID() + ") " +
                    "checked out to -> User: " + radioButtons.getSelectedToggle().toString()));
        });

        box.getChildren().addAll(title, bookID, check);

        return box;
    }

    private GridPane populateUserList() {
        totalRows = 0;
        GridPane pane = new GridPane();

        pane.setPadding(new Insets(4));
        pane.setVgap(4);
        pane.setHgap(8);

        radioButtons = new ToggleGroup();

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

            RadioButton uID = new RadioButton(user.getUserID());
            Text fName = new Text(user.getFirstName());
            Text lName = new Text(user.getLastName());
            Text aType = new Text(type);

            uID.setToggleGroup(radioButtons);

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
