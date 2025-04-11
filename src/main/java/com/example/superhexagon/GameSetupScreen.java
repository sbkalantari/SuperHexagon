package com.example.superhexagon;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

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
        Rectangle background = new Rectangle(0, 0, 800, 600);
        background.setFill(new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, Color.DARKSLATEBLUE), new Stop(1, Color.MIDNIGHTBLUE)));
        pane.getChildren().add(background);

        javafx.scene.text.Text title = new javafx.scene.text.Text("Enter Your Name");
        title.setFont(Font.font("Verdana", 48));
        title.setFill(Color.WHITE);
        title.setLayoutX(400 - title.getBoundsInLocal().getWidth() / 2);
        title.setLayoutY(100);

        VBox setup = new VBox(20);
        setup.setAlignment(Pos.CENTER);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setStyle("-fx-padding: 10px; -fx-background-color: #ffffff; -fx-border-color: #3498db; -fx-border-radius: 5px;");
        nameField.setMaxWidth(300);

        Button startButton = new Button("Start Game");
        startButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12px; -fx-background-radius: 10px;");
        startButton.setMinWidth(200);
        startButton.setMaxWidth(200);
        startButton.setOnAction(e -> {
            String name = nameField.getText().isEmpty() ? "Player" : nameField.getText();
            controller.startNewGame(name);
        });

        setup.getChildren().addAll(nameField, startButton);

        StackPane centerPane = new StackPane(setup);
        centerPane.setPrefSize(800, 600);

        pane.getChildren().addAll(title, centerPane);
    }

    public Pane getPane() {
        return pane;
    }
}