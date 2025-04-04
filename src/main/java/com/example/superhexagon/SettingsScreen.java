package com.example.superhexagon;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
        VBox settingsBox = new VBox(10);
        settingsBox.setLayoutX(350);
        settingsBox.setLayoutY(200);

        CheckBox musicCheck = new CheckBox("Enable Background Music");
        musicCheck.setSelected(controller.isMusicEnabled());
        musicCheck.setOnAction(e -> controller.setMusicEnabled(musicCheck.isSelected()));

        CheckBox historyCheck = new CheckBox("Save Game History");
        historyCheck.setSelected(controller.isSaveHistory());
        historyCheck.setOnAction(e -> controller.setSaveHistory(historyCheck.isSelected()));

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showMainMenu());

        settingsBox.getChildren().addAll(musicCheck, historyCheck, backButton);
        pane.getChildren().add(settingsBox);
    }

    public Pane getPane() {
        return pane;
    }
}