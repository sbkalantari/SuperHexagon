package com.example.superhexagon;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
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
    private final Pane pane;
    private Stage stage;
    private final GameController controller;
    private Player player;
    private final List<Wall> walls;
    private final Group groupWalls;
    private Polygon core;
    private double gameSpeed = 1.0;
    private Timeline gameLoop;
    private double elapsedTime = 0;
    private Text timeText;
    private Text recordText;
    private String playerName;
    private MediaPlayer mediaPlayer;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private double rotationAngle = 0;
    private double coreRadius = 50;
    private int lastPatternTick = 0;
    private final int patternInterval = 60;
    private final TrapFactory trapFactory;
    private double rotationSpeed = 0.5;
    private int swapTime = 0;
    private final Random random = new Random();

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
        this.trapFactory = new TrapFactory(this.groupWalls, this.walls);
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
        recordText = new Text(10, 40, "Record: " + String.format("%.1f", controller.getBestGameData().duration));
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

        pane.requestFocus();
    }

    private void update() {
        elapsedTime += 0.016 * gameSpeed;
        timeText.setText("Time: " + String.format("%.1f", elapsedTime));
        rotationAngle = (rotationAngle + rotationSpeed * gameSpeed) % 360;

        swapTime ++;
        if (swapTime % 300 == 0){
            if (random.nextInt(0, 1) == 0){
                swapTime = 0;
                rotationSpeed = -rotationSpeed;
            }
        }

        // Update player rotation based on key states
        if (leftPressed && !rightPressed) player.rotate(-1.2 * gameSpeed * Player.ROTATION_SPEED);
        if (rightPressed && !leftPressed) player.rotate(1.2 * gameSpeed * Player.ROTATION_SPEED);
        if (!rightPressed && !leftPressed) player.rotate(rotationSpeed * gameSpeed);
        player.update(gameSpeed);

        lastPatternTick++;
        if (lastPatternTick >= patternInterval) {
            lastPatternTick = 0;
            trapFactory.produce();
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

        javafx.scene.control.Button retryButton = new javafx.scene.control.Button("Retry");
        retryButton.setLayoutX(300);
        retryButton.setLayoutY(200);
        retryButton.setOnAction(e -> {
            controller.startNewGame(playerName);
        });

        javafx.scene.control.Button menuButton = new javafx.scene.control.Button("Back to Menu");
        menuButton.setLayoutX(370);
        menuButton.setLayoutY(200);
        menuButton.setOnAction(e -> controller.showMainMenu());


        pane.getChildren().addAll(gameOverText, scoreText, retryButton, menuButton);
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