package com.strajag.brick_breaker.game;

public class Ball implements Cloneable {
    //public declarations only for debugging, remove later
    Player player;
    int size;
    public int x;
    public int y;
    int speedX;
    int speedY;
    public int directionX;
    public int directionY;
    boolean isAlive;

    Ball(Player player) {
        this.player = player;
        isAlive = true;
    }

    public Player getPlayer() {
        return player;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDirectionX() {
        return directionX;
    }

    public int getDirectionY() {
        return directionY;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Object clone(){
        try {
            return super.clone();
        } catch (Exception ignore) {}
        return this;
    }
}
