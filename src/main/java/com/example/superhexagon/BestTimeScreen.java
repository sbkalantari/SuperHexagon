package com.example.superhexagon;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.geometry.Insets;
import javafx.scene.shape.Rectangle;

import java.time.format.DateTimeFormatter;

public class BestTimeScreen {
    private final Pane pane;
    private final Stage stage;
    private final GameController controller;

    public BestTimeScreen(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.pane = new Pane();
        initializeBestTimeScreen();
    }

    private void initializeBestTimeScreen() {
        if (controller.getGameHistory().isEmpty()) {
            showNoGameDataDialog();
            return;
        }
        Rectangle background = new Rectangle(0, 0, 800, 600);
        background.setFill(new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, Color.DARKSLATEBLUE), new Stop(1, Color.MIDNIGHTBLUE)));
        pane.getChildren().add(background);

        Text bestTimeLabel = new Text(100, 100, "Best Time:");
        bestTimeLabel.setFont(Font.font("Verdana", 36));
        bestTimeLabel.setFill(Color.WHITE);

        GameData bestGame = controller.getBestGameData();
        double bestTime = bestGame.getDuration();
        String playerName = bestGame.playerName;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy - HH:mm:ss");
        String formattedDate = bestGame.dateTime.format(formatter);

        Text bestTimeText = new Text(100, 160, String.format("Time: %.1f seconds", bestTime));
        bestTimeText.setFont(Font.font("Arial", 28));
        bestTimeText.setFill(Color.GOLD);

        Text playerNameText = new Text(100, 220, "Achieved by: " + playerName);
        playerNameText.setFont(Font.font("Arial", 20));
        playerNameText.setFill(Color.LIGHTGRAY);

        Text dateText = new Text(100, 260, "Achieved on: " + formattedDate);
        dateText.setFont(Font.font("Arial", 18));
        dateText.setFill(Color.LIGHTGRAY);

        Button backButton = new Button("Back to Main Menu");
        backButton.setLayoutX(300);
        backButton.setLayoutY(300);
        backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 10px;");
        backButton.setOnAction(e -> controller.showMainMenu());

        pane.getChildren().addAll(bestTimeLabel, bestTimeText, playerNameText, dateText, backButton);
    }


    private void showNoGameDataDialog() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("No Game Data");
        alert.setHeaderText("No game data has been recorded.");
        alert.setContentText("It looks like you haven't played any games yet. Please play and record a game first.");

        alert.showAndWait();
        controller.showMainMenu();
    }

    public Pane getPane() {
        return pane;
    }
}
