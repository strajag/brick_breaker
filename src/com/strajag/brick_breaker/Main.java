package com.strajag.brick_breaker;

import com.strajag.brick_breaker.frame.Frame;
import com.strajag.brick_breaker.frame.FrameSettings;
import com.strajag.brick_breaker.game.Game;
import com.strajag.brick_breaker.game.GameSettings;

class Main {

    public static void main(String[] args) {
        int screenWidth = 800;
        int screenHeight = 800;
        FrameSettings frameSettings = new FrameSettings(screenWidth, screenHeight);
        int gameResolution = Math.min(screenWidth, screenHeight);
        GameSettings gameSettings = new GameSettings(gameResolution);
        Game game = new Game(gameSettings);
        new Frame(frameSettings, game);
    }
}

//TO DO:
//implement method to change ball movement angle depending on platform position
//balls collision