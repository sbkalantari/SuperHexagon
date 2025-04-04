package com.example.superhexagon;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainMenu {
    private final Pane pane;
    private final Stage stage;
    private final GameController controller;

    public MainMenu(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.pane = new Pane();
        initializeMenu();
    }

    private void initializeMenu() {
        VBox menu = new VBox(10);
        menu.setLayoutX(350);
        menu.setLayoutY(200);

        Button startButton = new Button("Start New Game");
        startButton.setOnAction(e -> stage.setScene(new Scene(new GameSetupScreen(stage, controller).getPane(), 800, 600)));

        Button bestTimeButton = new Button("View Best Time");
        bestTimeButton.setOnAction(e -> {
            // Show best time dialog (implement as needed)
        });

        Button historyButton = new Button("Game History");
        historyButton.setOnAction(e -> stage.setScene(new Scene(new GameHistoryScreen(stage, controller).getPane(), 800, 600)));

        Button settingsButton = new Button("Settings");
        settingsButton.setOnAction(e -> stage.setScene(new Scene(new SettingsScreen(stage, controller).getPane(), 800, 600)));

        Button exitButton = new Button("Exit Game");
        exitButton.setOnAction(e -> stage.close());

        menu.getChildren().addAll(startButton, bestTimeButton, historyButton, settingsButton, exitButton);
        pane.getChildren().add(menu);
    }

    public Pane getPane() {
        return pane;
    }
}