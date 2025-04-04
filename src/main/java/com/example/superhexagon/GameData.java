package com.example.superhexagon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.ZonedDateTime;

public class GameData {
    @Expose
    @SerializedName("playerName")
    public String playerName;

    @Expose
    @SerializedName("dateTime")
    public ZonedDateTime dateTime;

    @Expose
    @SerializedName("duration")
    public double duration;


    public GameData(String playerName, ZonedDateTime dateTime, double duration) {
        this.playerName = playerName;
        this.dateTime = dateTime;
        this.duration = duration;
    }

    public GameData(){}

    public double getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %.1f seconds", playerName, dateTime.toString(), duration);
    }
}