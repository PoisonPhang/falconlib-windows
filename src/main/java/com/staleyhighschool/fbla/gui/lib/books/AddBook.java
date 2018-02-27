package com.staleyhighschool.fbla.gui.lib.books;

import com.staleyhighschool.fbla.gui.Main;
import com.staleyhighschool.fbla.library.Library;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddBook {

    private Scene addBook;
    private BorderPane layout;
    private HBox buttons;
    private HBox input;
    private VBox mainContent;

    private TextField title;
    private TextField author;

    public AddBook() {
        title = new TextField();
        author = new TextField();
        title.setPromptText("Title");
        author.setPromptText("Author");

        mainContent.getChildren().addAll(input, buttons);

        layout = new BorderPane();
        layout.setLeft(Main.generateNavigation());
        layout.setCenter(mainContent);

        addBook = new Scene(layout, 960, 540);

        newBook();
    }

    public Scene getAddBook() {
        return addBook;
    }

    private void newBook() {
        Main.manageBooks.appendList(Main.library.addBook(title.getText(), author.getText()));
    }
}
