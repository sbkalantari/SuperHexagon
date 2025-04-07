package com.example.superhexagon;

import com.google.gson.GsonBuilder;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.google.gson.Gson;
import java.io.*;

public class GameController {
    private final Stage stage;
    private List<GameData> gameHistory;
    private boolean musicEnabled = true;
    private boolean saveHistory = true;
    private static final String HISTORY_FILE = "game_history.json";
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public GameController(Stage stage) {
        this.stage = stage;
        loadGameHistory();
    }

    public void showMainMenu() {
        stage.setScene(new Scene(new MainMenu(stage, this).getPane(), 800, 600));
    }

    public void startNewGame(String playerName) {
        GameScreen gameScreen = new GameScreen(stage, this, playerName);
        stage.setScene(new Scene(gameScreen.getPane(), 800, 600));
        gameScreen.startGame();
    }

    public void saveGameData(GameData data) {
        if (saveHistory) {
            gameHistory.add(data);
            try (Writer writer = new FileWriter(HISTORY_FILE)) {
                gson.toJson(gameHistory, writer);
            } catch (IOException e) {
                e.printStackTrace(System.out);
            }
        }
    }

    private void loadGameHistory() {
        try (Reader reader = new FileReader(HISTORY_FILE)) {
            GameData[] data = gson.fromJson(reader, GameData[].class);
            gameHistory = data != null ? new ArrayList<>(List.of(data)) : new ArrayList<>();
        } catch (IOException e) {
            gameHistory = new ArrayList<>();
        }
    }

    public List<GameData> getGameHistory() {
        return gameHistory;
    }

    public GameData getBestGameData() {
        return gameHistory.stream()
                .max(Comparator.comparingDouble(GameData::getDuration))
                .orElse(new GameData("Unknown", ZonedDateTime.now(), 0));
    }

    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
//        this.musicEnabled = false;
    }

    public void setSaveHistory(boolean save) {
        this.saveHistory = save;
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public boolean isSaveHistory() {
        return saveHistory;
    }
}