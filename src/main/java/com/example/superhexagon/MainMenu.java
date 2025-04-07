package com.example.superhexagon;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.scene.shape.Rectangle;

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
        Rectangle background = new Rectangle(0, 0, 800, 600);
        background.setFill(new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, Color.DARKSLATEBLUE), new Stop(1, Color.MIDNIGHTBLUE)));
        pane.getChildren().add(background);

        Text title = new Text("Super Hexagon");
        title.setFont(Font.font("Verdana", 48));
        title.setFill(Color.WHITE);
        title.setLayoutX(400 - title.getBoundsInLocal().getWidth() / 2);
        title.setLayoutY(150);

        VBox menu = new VBox(15);
        menu.setLayoutX(300);
        menu.setLayoutY(200);
        menu.setAlignment(Pos.CENTER);

        Button startButton = createStyledButton("Start New Game");
        startButton.setOnAction(e -> stage.setScene(new Scene(new GameSetupScreen(stage, controller).getPane(), 800, 600)));

        Button bestTimeButton = createStyledButton("View Best Time");
        bestTimeButton.setOnAction(e -> stage.setScene(new Scene(new BestTimeScreen(stage, controller).getPane(), 800, 600)));

        Button historyButton = createStyledButton("Game History");
        historyButton.setOnAction(e -> stage.setScene(new Scene(new GameHistoryScreen(stage, controller).getPane(), 800, 600)));

        Button settingsButton = createStyledButton("Settings");
        settingsButton.setOnAction(e -> stage.setScene(new Scene(new SettingsScreen(stage, controller).getPane(), 800, 600)));

        Button exitButton = createStyledButton("Exit Game");
        exitButton.setOnAction(e -> stage.close());

        menu.getChildren().addAll(startButton, bestTimeButton, historyButton, settingsButton, exitButton);

        pane.getChildren().addAll(title, menu);
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12px;");
        button.setMinWidth(200);
        button.setMaxWidth(200);
        button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12px; -fx-background-radius: 10px;");

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12px; -fx-background-radius: 10px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 12px; -fx-background-radius: 10px;"));

        return button;
    }

    public Pane getPane() {
        return pane;
    }
}
