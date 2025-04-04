package com.example.superhexagon;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class GameSetupScreen {
    private Pane pane;
    private Stage stage;
    private GameController controller;

    public GameSetupScreen(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.pane = new Pane();
        initializeSetup();
    }

    private void initializeSetup() {
        VBox setup = new VBox(10);
        setup.setLayoutX(350);
        setup.setLayoutY(200);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");

        Button startButton = new Button("Start Game");
        startButton.setOnAction(e -> {
            String name = nameField.getText().isEmpty() ? "Player" : nameField.getText();
            controller.startNewGame(name);
        });

        setup.getChildren().addAll(nameField, startButton);
        pane.getChildren().add(setup);
    }

    public Pane getPane() {
        return pane;
    }
}