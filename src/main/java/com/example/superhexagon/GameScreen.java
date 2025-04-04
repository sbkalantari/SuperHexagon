package com.example.superhexagon;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class GameScreen {
    private Pane pane;
    private Stage stage;
    private GameController controller;
    private Player player;
    private List<Wall> walls;
    private Group groupWalls;
    private Polygon core;
    private double rotationSpeed = 1.0;
    private double gameSpeed = 1.0;
    private Timeline gameLoop;
    private double elapsedTime = 0;
    private Text timeText;
    private Text recordText;
    private String playerName;
    private Random random = new Random();
    private MediaPlayer mediaPlayer;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private double rotationAngle = 0;
    private double coreRadius = 50;

    public GameScreen(Stage stage, GameController controller, String playerName) {
        this.stage = stage;
        this.controller = controller;
        this.playerName = playerName;
        this.pane = new Pane();
        this.walls = new ArrayList<>();
        this.groupWalls = new Group();
        initializeGame();
        pane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                pane.requestFocus();
            }
        });
    }
    private void initializeGame() {
        // Core hexagon
        core = createHexagon(400, 300, 50);
        core.setFill(Color.BLACK);
        pane.getChildren().add(core);
        pane.getChildren().add(groupWalls);

        // Player
        player = new Player(400, 300, 70);
        pane.getChildren().add(player.getShape());

        // UI
        timeText = new Text(10, 20, "Time: 0.0");
        recordText = new Text(10, 40, "Record: " + String.format("%.1f", controller.getBestTime()));
        pane.getChildren().addAll(timeText, recordText);

        // Background
        pane.setStyle("-fx-background-color: #333333;");

        // Input handling
        pane.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) leftPressed = true;
            if (event.getCode() == KeyCode.RIGHT) rightPressed = true;
            if (event.getCode() == KeyCode.P) togglePause();
        });
        pane.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) leftPressed = false;
            if (event.getCode() == KeyCode.RIGHT) rightPressed = false;
        });
        pane.setFocusTraversable(true);
        pane.requestFocus();

        // Music
        if (controller.isMusicEnabled()) {
            Media sound = new Media(getClass().getResource("/theme.mp3").toString());
            mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
    }

    public void startGame() {
        gameLoop = new Timeline(new KeyFrame(Duration.millis(16), e -> update()));
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();

//        RotateTransition rt = new RotateTransition(Duration.seconds(10), core);
//        rt.setByAngle(360);
//        rt.setCycleCount(Animation.INDEFINITE);
//        rt.play();

//        Rotate rotate = new Rotate(0, 400, 300);
//        this.groupWalls.getTransforms().add(rotate);
//        core.getTransforms().add(rotate);
//
//        Timeline timeline = new Timeline(
//                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
//                new KeyFrame(Duration.seconds(10), new KeyValue(rotate.angleProperty(), 360))
//        );
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();

        pane.requestFocus();
    }

    private void update() {
        elapsedTime += 0.016 * gameSpeed;
        timeText.setText("Time: " + String.format("%.1f", elapsedTime));
        rotationAngle += 0.5 * gameSpeed;

        // Update player rotation based on key states
        if (leftPressed && !rightPressed) player.rotate(-1 * gameSpeed * Player.ROTATION_SPEED);
        if (rightPressed && !leftPressed) player.rotate(1 * gameSpeed * Player.ROTATION_SPEED);
        if (!rightPressed && !leftPressed) player.rotate(0.5 * gameSpeed);
        player.update(gameSpeed);

        // Spawn walls
        if (random.nextDouble() < 0.02 * gameSpeed) {
            Wall wall = new Wall(400, 300, random.nextInt(6));
            walls.add(wall);
            groupWalls.getChildren().add(wall.getShape());
        }

        // Update walls
        walls.forEach(wall -> wall.update(gameSpeed, rotationAngle));
        walls.removeIf(wall -> {
            if (wall.isAtCenter()){
                groupWalls.getChildren().remove(wall.getShape());
                return true;
            }
            if (wall.intersects(player)) endGame();
            return false;
        });
        updateCore();

        // Increase difficulty
        gameSpeed = 1.0 + elapsedTime / 60.0;

    }

    private void endGame() {
        gameLoop.stop();
        if (mediaPlayer != null) mediaPlayer.stop();
        controller.saveGameData(new GameData(playerName, ZonedDateTime.now(), elapsedTime));

        javafx.scene.effect.BoxBlur blur = new javafx.scene.effect.BoxBlur(5, 5, 3);
        pane.getChildren().forEach(child -> child.setEffect(blur));

        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFill(Color.RED);
        gameOverText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
        gameOverText.setX(250);
        gameOverText.setY(100);

        Text scoreText = new Text("Your time: " + String.format("%.1f", elapsedTime) + "s");
        scoreText.setFill(Color.WHITE);
        scoreText.setStyle("-fx-font-size: 24px;");
        scoreText.setX(300);
        scoreText.setY(150);


        pane.getChildren().addAll(gameOverText, scoreText);
//        controller.showMainMenu();
    }

    private void togglePause() {
        if (gameLoop.getStatus() == Animation.Status.RUNNING) {
            gameLoop.pause();
            if (mediaPlayer != null) mediaPlayer.pause();
        } else {
            gameLoop.play();
            if (mediaPlayer != null) mediaPlayer.play();
        }
    }

    private Polygon createHexagon(double centerX, double centerY, double radius) {
        Polygon hexagon = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            hexagon.getPoints().addAll(
                    centerX + radius * Math.cos(angle),
                    centerY + radius * Math.sin(angle)
            );
        }
        return hexagon;
    }

    public Pane getPane() {
        return pane;
    }

    private void updateCore() {
        double angleRad = Math.toRadians(rotationAngle);
        core.getPoints().clear();

        for (int i = 0; i < 6; i++) {
            double baseAngle = Math.toRadians(60 * i) + angleRad;
            double x = 400 + coreRadius  * Math.cos(baseAngle);
            double y = 300 + coreRadius  * Math.sin(baseAngle);
            core.getPoints().addAll(x, y);
        }
    }
}