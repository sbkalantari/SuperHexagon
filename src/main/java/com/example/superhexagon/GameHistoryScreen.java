package com.example.superhexagon;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import java.time.format.DateTimeFormatter;

public class GameHistoryScreen {
    private Pane pane;
    private Stage stage;
    private GameController controller;

    public GameHistoryScreen(Stage stage, GameController controller) {
        this.stage = stage;
        this.controller = controller;
        this.pane = new Pane();
        initializeHistory();
    }

    private void initializeHistory() {
        Rectangle background = new Rectangle(0, 0, 800, 600);
        background.setFill(new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, Color.DARKSLATEBLUE), new Stop(1, Color.MIDNIGHTBLUE)));
        pane.getChildren().add(background);

        Text title = new Text("Game History");
        title.setFont(Font.font("Verdana", 48));
        title.setFill(Color.WHITE);
        title.setLayoutX(250);
        title.setLayoutY(100);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(700, 320);
        scrollPane.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-background-insets: 0;" +
                        "-fx-padding: 0;" +
                        "-fx-border-width: 0;" +
                        "scroll-pane-background: transparent;" +
                        "viewport-border-width: 0;");

        VBox content = new VBox(10);
        content.setPadding(new Insets(10));
        content.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, null,
                        new Stop(0, Color.DARKSLATEBLUE), new Stop(1, Color.MIDNIGHTBLUE)),
                CornerRadii.EMPTY, Insets.EMPTY)));

        for (GameData data : controller.getGameHistory()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy - HH:mm:ss");
            String formattedDate = data.dateTime.format(formatter);

            String entryText = String.format("Player: %s\nDate: %s\nDuration: %.1f seconds",
                    data.playerName, formattedDate, data.duration);
            Text gameHistoryEntry = new Text(entryText);
            gameHistoryEntry.setFont(Font.font("Arial", 18));
            gameHistoryEntry.setFill(Color.LIGHTGRAY);
            content.getChildren().add(gameHistoryEntry);
        }

        StackPane contentWrapper = new StackPane(content);
        contentWrapper.setBackground(new Background(new BackgroundFill(
                new LinearGradient(0, 0, 1, 1, true, null,
                        new Stop(0, Color.DARKSLATEBLUE), new Stop(1, Color.MIDNIGHTBLUE)),
                CornerRadii.EMPTY, Insets.EMPTY)));

        scrollPane.setContent(contentWrapper);
        scrollPane.setFitToWidth(true);

        for (GameData data : controller.getGameHistory()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy - HH:mm:ss");
            String formattedDate = data.dateTime.format(formatter);

            String entryText = String.format("Player: %s\nDate: %s\nDuration: %.1f seconds",
                    data.playerName, formattedDate, data.duration);
            Text gameHistoryEntry = new Text(entryText);
            gameHistoryEntry.setFont(Font.font("Arial", 18));
            gameHistoryEntry.setFill(Color.LIGHTGRAY);
            content.getChildren().add(gameHistoryEntry);
        }

        StackPane scrollContainer = new StackPane(content);
        scrollContainer.setPadding(new Insets(10));
        scrollPane.setContent(scrollContainer);

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 18px; " +
                "-fx-padding: 12px; -fx-background-radius: 10px;");
        backButton.setMinWidth(200);
        backButton.setMaxWidth(200);
        backButton.setOnAction(e -> controller.showMainMenu());

        VBox historyBox = new VBox(20);
        historyBox.setLayoutX(50);
        historyBox.setLayoutY(150);
        historyBox.setAlignment(Pos.CENTER);
        historyBox.getChildren().addAll(scrollPane, backButton);

        pane.getChildren().addAll(title, historyBox);
    }

    public Pane getPane() {
        return pane;
    }
}