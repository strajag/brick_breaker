package com.strajag.brick_breaker.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Player {

    int no;
    Color color;
    List<Ball> balls = new ArrayList<>();
    Platform platform;
    int score;

    Player(int no, Color color) {
        this.no = no;
        this.color = color;
        score = 0;
    }

    public void setPlatformDirection(int direction) {
        if (direction < -1 || direction > 1) { return; }
        platform.direction = direction;
    }

    public int getNo() {
        return no;
    }

    public Color getColor() {
        return color;
    }

    public int getBallsSize() {
        return balls.size();
    }

    public Ball getBall(int i) {
        return balls.get(i);
    }

    public Platform getPlatform() {
        return platform;
    }

    public int getScore() {
        return score;
    }
}
