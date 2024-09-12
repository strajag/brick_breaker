package com.strajag.brick_breaker.game;

public class GameSettings {

    int gameResolution;

    int playersNo;

    int columns;
    int rows;

    int gridWidth;
    int gridHeight;

    int brickWidth;
    int brickHeight;
    int ballSize;
    int platformWidth;
    int platformHeight;

    int ballSpeed;
    int platformSpeed;

    boolean isBricksInCenter;
    boolean isBrickSquare;

    int ballsNo;

    boolean isP1bot;
    boolean isP2bot;
    boolean isP3bot;
    boolean isP4bot;

    public GameSettings(int gameResolution) {
        this.gameResolution = gameResolution;
        setSettings();
    }

    private void setSettings() {
        playersNo = 4;
        ballsNo = 1;

        columns = 15;
        rows = 8;

        isBricksInCenter = false;
        if (playersNo > 1) {
            isBricksInCenter = true;
        }
        isBrickSquare = true;

        isP1bot = true;
        isP2bot = true;
        isP3bot = true;
        isP4bot = true;

        setGridSize();

        brickWidth = gridWidth - 2;
        brickHeight = gridHeight - 2;
        ballSize = gridHeight - 2;
        platformWidth = gridHeight * 8;
        platformHeight = gridHeight / 3;
        ballSpeed = 3;
        platformSpeed = 20;
    }
    //calculate grid size to have even square in rows and correct rows number if grid size is not whole number
    private void setGridSize() {
        int resolution = gameResolution;

        if (isBricksInCenter) {
            resolution = gameResolution / 2;
        }

        int counter = 0;
        while (true) {
            if (resolution % (columns + counter) == 0) {
                gridWidth = resolution / (columns + counter);
                columns += counter;
                break;
            }
            if (resolution % (columns - counter) == 0) {
                gridWidth = resolution / (columns - counter);
                columns -= counter;
                break;
            }
            counter++;
        }

        if (isBricksInCenter) {
            rows = columns;
        } else {
            rows = columns / 2;
        }

        if (isBrickSquare) {
            gridHeight = gridWidth;
        } else {
            gridHeight = gridWidth / 2;
            rows *= 2;
        }
    }

    public int gameResolution() {
        return gameResolution;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGameResolution() {
        return gameResolution;
    }
}
