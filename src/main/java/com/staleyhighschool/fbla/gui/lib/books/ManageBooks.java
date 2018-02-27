package com.staleyhighschool.fbla.gui.lib.books;

import com.staleyhighschool.fbla.gui.Main;
import com.staleyhighschool.fbla.library.Book;
import com.staleyhighschool.fbla.library.Library;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class ManageBooks {

    private Scene manageBooks;
    private BorderPane layout;
    private ScrollPane mainContent;
    private GridPane bookList;

    private final String name = " | Manage Books";

    public ManageBooks() {
        bookList = populateBookList();
        mainContent = new ScrollPane();
        mainContent.setContent(bookList);
        layout = new BorderPane();
        layout.setLeft(Main.generateNavigation());
        layout.setCenter(mainContent);
        manageBooks = new Scene(layout, 960, 540);
    }

    public Scene getManageBooks() {
        return manageBooks;
    }

    public String getName() {
        return name;
    }

    private GridPane populateBookList() {
        GridPane pane = new GridPane();

        pane.setPadding(new Insets(4));
        pane.setVgap(4);
        pane.setHgap(8);

        int row = 1;

        Text titleTag = new Text("Title");
        Text authorTag = new Text("Author");
        Text idTag = new Text("ID");
        Text isOutTag = new Text("Is Out");
        Text isLateTag = new Text("Is Late");

        pane.add(titleTag, 0, 0);
        pane.add(authorTag, 1, 0);
        pane.add(idTag, 2, 0);
        pane.add(isOutTag, 3, 0);
        pane.add(isLateTag, 4, 0);

        for (Book book : Library.bookList) {

            String isOutT =  "false";
            String isLateT = "false";

            if (book.isOut()) {
                isOutT = "true";
            }
            if (book.isLate()) {
                isLateT = "true";
            }

            Text title = new Text(book.getBookTitle());
            Text author = new Text(book.getBookAuthor());
            Text id = new Text(book.getBookID());
            Text isOut = new Text(isOutT);
            Text isLate = new Text(isLateT);

            pane.add(title, 0, row);
            pane.add(author, 1, row);
            pane.add(id, 2, row);
            pane.add(isOut, 3, row);
            pane.add(isLate, 4, row);

            row++;
        }

        return pane;
    }
}
