package com.example.superhexagon;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.geometry.Point3D;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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

    public void update(double speed) {
        distance -= 2 * speed;
        shape.getPoints().clear();
        double angle = Math.toRadians(60 * section);
        double nextAngle = Math.toRadians(60 * (section + 1));

        shape.getPoints().addAll(
                centerX + distance * Math.cos(angle), centerY + distance * Math.sin(angle),
                centerX + distance * Math.cos(nextAngle), centerY + distance * Math.sin(nextAngle),
                centerX + (distance - 50) * Math.cos(nextAngle), centerY + (distance - 50) * Math.sin(nextAngle),
                centerX + (distance - 50) * Math.cos(angle), centerY + (distance - 50) * Math.sin(angle)
        );
    }

    public boolean isAtCenter() {
        return distance <= 50;
    }

    public boolean intersects(Player player) {
        return shape.getBoundsInParent().intersects(player.getShape().getBoundsInParent());
    }

    public Polygon getShape() {
        return shape;
    }
}