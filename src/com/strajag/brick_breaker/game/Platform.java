package com.strajag.brick_breaker.game;

public class Platform {

    Player player;
    int width;
    int height;
    int x;
    int y;
    int speed;
    int direction;

    Platform(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }
}
