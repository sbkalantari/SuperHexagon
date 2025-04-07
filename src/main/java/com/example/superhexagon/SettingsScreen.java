package com.example.superhexagon;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;

public class SettingsScreen {
    private Pane pane;
    private Stage stage;
    private GameController controller;

    public SettingsScreen(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.pane = new Pane();
        initializeSettings();
    }

    private void initializeSettings() {
        Rectangle background = new Rectangle(0, 0, 800, 600);
        background.setFill(new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, Color.DARKSLATEBLUE), new Stop(1, Color.MIDNIGHTBLUE)));
        pane.getChildren().add(background);

        Text title = new Text("Settings");
        title.setFont(Font.font("Verdana", 48));
        title.setFill(Color.WHITE);
        title.setLayoutX(320);
        title.setLayoutY(100);

        VBox settingsBox = new VBox(20);
        settingsBox.setLayoutX(300);
        settingsBox.setLayoutY(200);
        settingsBox.setAlignment(Pos.CENTER);

        CheckBox musicCheck = new CheckBox("Enable Background Music");
        musicCheck.setSelected(controller.isMusicEnabled());
        musicCheck.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");  // Custom styling for checkbox text
        musicCheck.setOnAction(e -> controller.setMusicEnabled(musicCheck.isSelected()));

        CheckBox historyCheck = new CheckBox("Save Game History");
        historyCheck.setSelected(controller.isSaveHistory());
        historyCheck.setStyle("-fx-text-fill: white; -fx-font-size: 20px;");
        historyCheck.setOnAction(e -> controller.setSaveHistory(historyCheck.isSelected()));

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12px; -fx-background-radius: 10px;");
        backButton.setMinWidth(200);
        backButton.setMaxWidth(200);
        backButton.setOnAction(e -> controller.showMainMenu());

        settingsBox.getChildren().addAll(musicCheck, historyCheck, backButton);
        pane.getChildren().addAll(title, settingsBox);
    }

    public Pane getPane() {
        return pane;
    }
}
