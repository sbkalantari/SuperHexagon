package com.example.superhexagon;

import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrapFactory {
    private int trapType1Start = 0;
    private int trapType2Start = 0;
    private Random random = new Random();
    private List<Wall> walls;
    private Group groupWalls;

    public TrapFactory(Group groupWalls, List<Wall> walls) {
        this.groupWalls = groupWalls;
        this.walls = walls;
    }
    public void produce(){
        WallPatternMode mode = WallPatternMode.values()[random.nextInt(WallPatternMode.values().length)];
        switch (mode) {
            case TYPE1:
                spawnType1();
                break;
            case TYPE2:
                spawnType2();
                break;
            case TYPE3:
                spawnType3();
                break;
        }
    }

    private void spawnType1() {
        for (int i = trapType1Start; i < 6; i += 2) {
            Wall wall = new Wall(400, 300, i);
            walls.add(wall);
            groupWalls.getChildren().add(wall.getShape());
        }
        trapType1Start = (trapType1Start + 1) %2;
    }

    private void spawnType2() {
        int start = random.nextInt(2) + trapType2Start;
        for (int i = 0; i < 4; i++) {
            int section = (start + i) % 6;
            Wall wall = new Wall(400, 300, section);
            walls.add(wall);
            groupWalls.getChildren().add(wall.getShape());
        }
        trapType2Start = (trapType2Start + 3) %6;
    }

    private void spawnType3() {
        int totalWalls = 4; // tweakable
        List<Integer> sections = new ArrayList<>();
        for (int i = 0; i < 6; i++) sections.add(i);
        java.util.Collections.shuffle(sections);
        for (int i = 0; i < totalWalls; i++) {
            Wall wall = new Wall(400, 300, sections.get(i));
            walls.add(wall);
            groupWalls.getChildren().add(wall.getShape());
        }
    }
}
