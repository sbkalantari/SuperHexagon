package com.example.superhexagon;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;

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

        Rectangle scrollBackground = new Rectangle(0, 0, 700, 320);
        scrollBackground.setFill(new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, Color.DARKSLATEBLUE), new Stop(1, Color.MIDNIGHTBLUE)));

        VBox content = new VBox(10);
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

        VBox scrollContent = new VBox(10);
        scrollContent.getChildren().addAll(content);

        scrollPane.setContent(scrollContent);
        scrollPane.setStyle("-fx-background-color: transparent;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: black; -fx-font-size: 18px; -fx-padding: 12px; -fx-background-radius: 10px;");
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
