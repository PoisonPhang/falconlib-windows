package com.staleyhighschool.fbla.gui.lib.managment;

import com.staleyhighschool.fbla.gui.Main;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ControlPanel {

  private final String name = " | Control Panel";

  private Scene scene;
  private HBox buttons;
  private BorderPane layout;

  public ControlPanel() {
    buttons = generateButtons();
    layout = new BorderPane();
    layout.setLeft(Main.generateNavigation());
    layout.setCenter(buttons);
    scene = new Scene(layout, 960, 540);
  }

  public Scene getScene() {
    return scene;
  }

  public String getName() {
    return name;
  }

  private HBox generateButtons() {
    HBox box = new HBox();

    Button manageRules = new Button("Manage Rules");
    Button viewLogs = new Button("View Logs");

    manageRules.setOnAction(e -> Main.changeScene(Main.manageRules.getScene()));
    viewLogs.setOnAction(e -> Main.changeScene(Main.logs.getLogs()));

    box.getChildren().addAll(manageRules, viewLogs);

    return box;
  }
}
