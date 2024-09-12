package com.strajag.brick_breaker.game;

import java.awt.*;

public class Brick {

    Color color;
    BrickType type;
    int x;
    int y;
    int width;
    int height;
    boolean isAlive;

    Brick(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        isAlive = true;
    }

    public Color getColor() {
        return color;
    }

    public BrickType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
