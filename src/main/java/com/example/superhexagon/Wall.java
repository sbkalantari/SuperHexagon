package com.example.superhexagon;

import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Wall {
    private Polygon shape;
    private double centerX, centerY;
    private double distance;
    private static final double WIDTH = 20;
    private static final int TOTAL_SECTIONS = 6;
    private int section;

    public Wall(double centerX, double centerY, int section) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.section = section % TOTAL_SECTIONS;
        this.distance = 300;
        this.shape = new Polygon();
        this.shape.setFill(Color.RED);
        update(0, 0); // initialize shape
    }

    public void update(double speed, double rotationAngle) {
        distance -= 2 * speed;
        shape.getPoints().clear();

        // Always normalize rotation to avoid float precision errors
        rotationAngle = rotationAngle % 360;

        double angle1 = Math.toRadians(60 * section + rotationAngle);
        double angle2 = Math.toRadians(60 * ((section + 1) % TOTAL_SECTIONS) + rotationAngle);

        double outer = distance;
        double inner = distance - WIDTH;

        shape.getPoints().addAll(
                centerX + outer * Math.cos(angle1), centerY + outer * Math.sin(angle1),
                centerX + outer * Math.cos(angle2), centerY + outer * Math.sin(angle2),
                centerX + inner * Math.cos(angle2), centerY + inner * Math.sin(angle2),
                centerX + inner * Math.cos(angle1), centerY + inner * Math.sin(angle1)
        );
    }

    public boolean isAtCenter() {
        return distance <= WIDTH + 50;
    }

    public boolean intersects(Player player) {
        Shape intersection = Shape.intersect(this.shape, player.getShape());
        return intersection.getBoundsInLocal().getWidth() > 0 &&
                intersection.getBoundsInLocal().getHeight() > 0;
    }

    public Polygon getShape() {
        return shape;
    }
}
