package com.example.superhexagon;

import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Wall {
    private Polygon shape;
    private double centerX, centerY;
    private double distance;
    private int section;

    public Wall(double centerX, double centerY, int section) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.section = section;
        this.distance = 300;
        this.shape = createTrapezoid();
        shape.setFill(Color.RED);
    }

    private Polygon createTrapezoid() {
        Polygon trapezoid = new Polygon();
        double angle = Math.toRadians(60 * section);
        double nextAngle = Math.toRadians(60 * (section + 1));

        trapezoid.getPoints().addAll(
                centerX + distance * Math.cos(angle), centerY + distance * Math.sin(angle),
                centerX + distance * Math.cos(nextAngle), centerY + distance * Math.sin(nextAngle),
                centerX + (distance - 50) * Math.cos(nextAngle), centerY + (distance - 50) * Math.sin(nextAngle),
                centerX + (distance - 50) * Math.cos(angle), centerY + (distance - 50) * Math.sin(angle)
        );
        return trapezoid;
    }

    public void update(double speed, double rotationAngle) {
        distance -= 2 * speed;
        shape.getPoints().clear();

        double baseAngle = Math.toRadians(60 * section + rotationAngle);
        double nextAngle = Math.toRadians(60 * (section + 1) + rotationAngle);

        shape.getPoints().addAll(
                centerX + distance * Math.cos(baseAngle), centerY + distance * Math.sin(baseAngle),
                centerX + distance * Math.cos(nextAngle), centerY + distance * Math.sin(nextAngle),
                centerX + (distance - 50) * Math.cos(nextAngle), centerY + (distance - 50) * Math.sin(nextAngle),
                centerX + (distance - 50) * Math.cos(baseAngle), centerY + (distance - 50) * Math.sin(baseAngle)
        );
    }


    public boolean isAtCenter() {
        return distance <= 100;
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