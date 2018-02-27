package com.staleyhighschool.fbla.gui.lib.books;

import com.staleyhighschool.fbla.gui.Main;
import com.staleyhighschool.fbla.library.Book;
import com.staleyhighschool.fbla.library.Library;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


public class ManageBooks {

    private Scene manageBooks;
    private BorderPane layout;
    private ScrollPane mainContent;
    private GridPane bookList;

    private List<Book> selectedBooks;

    private final String name = " | Manage Books";

    public ManageBooks() {
        bookList = populateBookList();
        mainContent = new ScrollPane();
        mainContent.setContent(bookList);

        layout = new BorderPane();
        layout.setLeft(Main.generateNavigation());
        layout.setCenter(mainContent);
        layout.setTop(topButtons());

        manageBooks = new Scene(layout, 960, 540);
    }

    public Scene getManageBooks() {
        return manageBooks;
    }

    public String getName() {
        return name;
    }

    private HBox topButtons() {
        HBox hBox = new HBox(10);

        Button checkOut = new Button("Check Out Selected");
        Button returnBooks = new Button("Return Selected");
        Button delete = new Button("Delete Selected");

//        checkOut.setOnAction(e -> Main.library.checkOutBook());
        returnBooks.setOnAction(e -> Main.library.returnBook(selectedBooks));
        delete.setOnAction(e -> Main.library.deleteBook(selectedBooks));

        hBox.getChildren().addAll(checkOut, returnBooks, delete);


        return hBox;
    }

    private GridPane populateBookList() {
        GridPane pane = new GridPane();

        pane.setPadding(new Insets(4));
        pane.setVgap(4);
        pane.setHgap(8);

        selectedBooks = new ArrayList<>();

        int wRow = 1;
        int tRow = 0;

        Text indexTag = new Text("Index");
        Text titleTag = new Text("Title");
        Text authorTag = new Text("Author");
        Text idTag = new Text("ID");
        Text isOutTag = new Text("Is Out");
        Text isLateTag = new Text("Is Late");

        pane.add(indexTag, 0, tRow);
        pane.add(titleTag, 1, tRow);
        pane.add(authorTag, 2, tRow);
        pane.add(idTag, 3, tRow);
        pane.add(isOutTag, 4, tRow);
        pane.add(isLateTag, 5, tRow);

        for (Book book : Library.bookList) {

            String isOutT =  "false";
            String isLateT = "false";

            if (book.isOut()) {
                isOutT = "true";
            }
            if (book.isLate()) {
                isLateT = "true";
            }

            Text index = new Text(String.valueOf(wRow));
            CheckBox title = new CheckBox(book.getBookTitle());
            Text author = new Text(book.getBookAuthor());
            Text id = new Text(book.getBookID());
            Text isOut = new Text(isOutT);
            Text isLate = new Text(isLateT);

            Button returnBook = new Button("Return");
            Button checkOutBook = new Button("Check Out");
            Button delete = new Button("Delete");

            returnBook.setOnAction(e -> Library.connection.userReturnBook(book));
            delete.setOnAction(e -> Library.connection.deleteBook(book));

            if (title.isSelected()) {
                selectedBooks.add(book);
            }

            pane.add(index, 0, wRow);
            pane.add(title, 1, wRow);
            pane.add(author, 2, wRow);
            pane.add(id, 3, wRow);
            pane.add(isOut, 4, wRow);
            pane.add(isLate, 5, wRow);

            if (book.isOut()) {
                pane.add(returnBook, 6, wRow);
            } else {
                pane.add(checkOutBook, 6, wRow);
            }
            pane.add(delete, 7, wRow);
            wRow++;
        }

        return pane;
    }
}
