package com.strajag.brick_breaker.game;

class Bot {

    Game game;
    int magicNumber = 50;

    Bot(Game game) {
        this.game = game;
    }

    void play() {
        if (game.gameSettings.isP1bot || game.gameSettings.isP2bot || game.gameSettings.isP3bot || game.gameSettings.isP4bot) {
            Player player;
            Platform platform;
            Ball ball;
            Ball closestBall;
            int platformCenter;
            int ballCenter;
            if (game.gameSettings.isP1bot) {
                player = game.players.get(0);
                platform = player.platform;
                //find the closest ball to platform height
                closestBall = new Ball(null);
                closestBall.size = 0; closestBall.x = 0; closestBall.y = 0;
                for (Player p : game.players) {
                    for (int i = 0; i < p.balls.size(); i++) {
                        ball = p.balls.get(i);
                        if (ball.y + ball.size > closestBall.y + closestBall.size && ball.y + ball.size <= platform.y) {
                            closestBall = ball;
                        }
                    }
                }
                //move platform
                if (closestBall.player != null) {
                    platformCenter = platform.x + platform.width / 2;
                    ballCenter = closestBall.x + closestBall.size / 2;
                    //The compare() method of Integer class of java.lang package compares two integer values (x, y) given
                    //as a parameter and returns the value zero if (x==y), if (x < y) then it returns a value less than zero and
                    //if (x > y) then it returns a value greater than zero.
                    if(Math.abs(ballCenter - platformCenter) < magicNumber)
                        platform.direction = 0;
                    else
                        platform.direction = Integer.compare(ballCenter, platformCenter);
                }
            }
            if (game.gameSettings.isP2bot && game.players.size() > 1) {
                player = game.players.get(1);
                platform = player.platform;
                //find the closest ball to platform height
                closestBall = new Ball(null);
                closestBall.size = 9999; closestBall.x = 9999; closestBall.y = 9999;
                for (Player p : game.players) {
                    for (int i = 0; i < p.balls.size(); i++) {
                        ball = p.balls.get(i);
                        if (ball.y < closestBall.y && ball.y >= platform.y + platform.height) {
                            closestBall = ball;
                        }
                    }
                }
                if (closestBall.player != null) {
                    //move platform
                    platformCenter = platform.x + platform.width / 2;
                    ballCenter = closestBall.x + closestBall.size / 2;
                    if(Math.abs(ballCenter - platformCenter) < magicNumber)
                        platform.direction = 0;
                    else
                        platform.direction = Integer.compare(ballCenter, platformCenter);
                }
            }
            if (game.gameSettings.isP3bot && game.players.size() > 2) {
                player = game.players.get(2);
                platform = player.platform;
                //find the closest ball to platform height
                closestBall = new Ball(null);
                closestBall.size = 9999; closestBall.x = 9999; closestBall.y = 9999;
                for (Player p : game.players) {
                    for (int i = 0; i < p.balls.size(); i++) {
                        ball = p.balls.get(i);
                        if (ball.x < closestBall.x && ball.x >= platform.x + platform.width) {
                            closestBall = ball;
                        }
                    }
                }
                //move platform
                if (closestBall.player != null) {
                    platformCenter = platform.y + platform.height / 2;
                    ballCenter = closestBall.y + closestBall.size / 2;
                    if(Math.abs(ballCenter - platformCenter) < magicNumber)
                        platform.direction = 0;
                    else
                        platform.direction = Integer.compare(ballCenter, platformCenter);
                }
            }
            if (game.gameSettings.isP4bot && game.players.size() > 3) {
                player = game.players.get(3);
                platform = player.platform;
                //find the closest ball to platform height
                closestBall = new Ball(null);
                closestBall.size = 0; closestBall.x = 0; closestBall.y = 0;
                for (Player p : game.players) {
                    for (int i = 0; i < p.balls.size(); i++) {
                        ball = p.balls.get(i);
                        if (ball.x + ball.size > closestBall.x + closestBall.size && ball.x + ball.size <= platform.x) {
                            closestBall = ball;
                        }
                    }
                }
                //move platform
                if (closestBall.player != null) {
                    platformCenter = platform.y + platform.height / 2;
                    ballCenter = closestBall.y + closestBall.size / 2;
                    if(Math.abs(ballCenter - platformCenter) < magicNumber)
                        platform.direction = 0;
                    else
                        platform.direction = Integer.compare(ballCenter, platformCenter);
                }
            }
        }
    }
}
