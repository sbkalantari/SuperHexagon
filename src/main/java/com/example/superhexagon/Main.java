package com.example.superhexagon;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        GameController controller = new GameController(primaryStage);
        MainMenu mainMenu = new MainMenu(primaryStage, controller);
        Scene scene = new Scene(mainMenu.getPane(), 800, 600);
        primaryStage.setTitle("Super Hexagon Clone");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}