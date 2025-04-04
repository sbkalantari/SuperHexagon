package com.example.superhexagon;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
        VBox historyBox = new VBox(10);
        historyBox.setLayoutX(50);
        historyBox.setLayoutY(50);

        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(5);
        for (GameData data : controller.getGameHistory()) {
            content.getChildren().add(new Text(data.toString()));
        }
        scrollPane.setContent(content);
        scrollPane.setPrefSize(700, 400);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showMainMenu());

        historyBox.getChildren().addAll(scrollPane, backButton);
        pane.getChildren().add(historyBox);
    }

    public Pane getPane() {
        return pane;
    }
}