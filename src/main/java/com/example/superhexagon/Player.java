package com.example.superhexagon;

import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

public class Player {
    private Polygon shape;
    private double centerX, centerY;
    private double radius;
    private double angle;
    private static final double ROTATION_SPEED = 180.0; // Degrees per second

    public Player(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.angle = 0;
        this.shape = createTriangle();
        shape.setFill(Color.WHITE);
    }

    private Polygon createTriangle() {
        Polygon triangle = new Polygon();
        double x = centerX + radius * Math.cos(Math.toRadians(angle));
        double y = centerY + radius * Math.sin(Math.toRadians(angle));
        triangle.getPoints().addAll(
                x, y,
                x + 10 * Math.cos(Math.toRadians(angle + 120)), y + 10 * Math.sin(Math.toRadians(angle + 120)),
                x + 10 * Math.cos(Math.toRadians(angle - 120)), y + 10 * Math.sin(Math.toRadians(angle - 120))
        );
        return triangle;
    }

    public void rotate(double direction) {
        // Direction is -1 for left, 1 for right; scaled by game speed
        angle += direction * ROTATION_SPEED * 0.016; // 0.016 seconds per frame
    }

    public void update(double speed) {
        // Automatic rotation removed; now fully controlled by key input
        shape.getPoints().clear();
        double x = centerX + radius * Math.cos(Math.toRadians(angle));
        double y = centerY + radius * Math.sin(Math.toRadians(angle));
        shape.getPoints().addAll(
                x, y,
                x + 10 * Math.cos(Math.toRadians(angle + 120)), y + 10 * Math.sin(Math.toRadians(angle + 120)),
                x + 10 * Math.cos(Math.toRadians(angle - 120)), y + 10 * Math.sin(Math.toRadians(angle - 120))
        );
    }

    public Polygon getShape() {
        return shape;
    }
}