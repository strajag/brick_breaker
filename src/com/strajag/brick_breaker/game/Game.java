package com.strajag.brick_breaker.game;

import com.strajag.brick_breaker.tools.Timer;
import com.strajag.brick_breaker.tools.TimerListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game implements TimerListener
{

    GameSettings gameSettings;

    List<Brick> bricks = new ArrayList<>();
    List<Player> players = new ArrayList<>();

    int delay = 16;
    Timer timer = new Timer(delay, this);

    TimerListener timerListener = null;

    boolean isGameOver = false;

    Random random = new Random();

    boolean isUpdating = false;

    Bot bot = new Bot(this);

    public Game(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        restart();
    }

    public void start() {
        if (timer.isRunning()) {
            timer.stop();
        } else {
            timer.start();
        }
    }

    public void restart() {
        timer.stop();
        players.clear();
        bricks.clear();
        setPlayers();
        setBricks();
        setPlatforms();
        setBalls();
        isGameOver = false;
        if (timerListener != null) {
            timerListener.timerPerformed(timer);
        }
    }

    public void setTimerListener(TimerListener timerListener) {
        this.timerListener = timerListener;
    }

    private void setBricks() {
        int startPosition = 0;
        if (gameSettings.isBricksInCenter) {
            startPosition = gameSettings.gameResolution / 4;
        }
        Brick brick;
        for (int i = 0; i < gameSettings.rows; i++) {
            for (int i2 = 0; i2 < gameSettings.columns; i2++) {
                int x = ((i2 * gameSettings.brickWidth) + i2 * (gameSettings.gridWidth - gameSettings.brickWidth)) + ((gameSettings.gridWidth - gameSettings.brickWidth) / 2) + startPosition;
                int y = (i * gameSettings.brickHeight) + i * (gameSettings.gridHeight - gameSettings.brickHeight) + ((gameSettings.gridHeight - gameSettings.brickHeight) / 2) + startPosition;
                brick = new Brick(x, y, gameSettings.brickWidth, gameSettings.brickHeight);

                if (getRandomBoolean(0.002f)) {
                    brick.type = BrickType.DEATH;
                    brick.color = new Color(73, 0, 0);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.BALL_SIZE_PLUS;
                    brick.color = new Color(157, 255, 157);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.BALL_SIZE_MINUS;
                    brick.color = new Color(82, 127, 82);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.BALL_SPEED_PLUS;
                    brick.color = new Color(161, 255, 233);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.BALL_SPEED_MINUS;
                    brick.color = new Color(86, 134, 122);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.PLATFORM_SIZE_PLUS;
                    brick.color = new Color(139, 183, 213);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.PLATFORM_SIZE_MINUS;
                    brick.color = new Color(72, 94, 109);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.PLATFORM_SPEED_PLUS;
                    brick.color = new Color(166, 146, 220);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.PLATFORM_SPEED_MINUS;
                    brick.color = new Color(110, 97, 146);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.PLUS;
                    brick.color = new Color(229, 227, 154);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.MINUS;
                    brick.color = new Color(120, 119, 81);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.BALL_1X;
                    brick.color = new Color(231, 170, 129);
                } else if (getRandomBoolean(0.05f)) {
                    brick.type = BrickType.BALL_2X;
                    brick.color = new Color(238, 120, 39);
                } else {
                    brick.type = BrickType.STANDARD;
                    brick.color = Color.LIGHT_GRAY;
                }
                bricks.add(brick);
            }
        }
    }

    boolean getRandomBoolean(float p){
        return random.nextFloat() < p;
    }

    private void setPlayers() {
        Color[] colors = new Color[4];
        colors[0] = new Color(255, 0, 0);
        colors[1] = new Color(0, 94, 255);
        colors[2] = new Color(8, 255, 0);
        colors[3] = new Color(255, 119, 0);
        for (int i = 0; i < gameSettings.playersNo; i++) {
            players.add(new Player(i + 1, colors[i]));
        }
    }

    private void setPlatforms() {
        Platform platform;
        for (Player player : players) {
            platform = new Platform(player);
            platform.speed = gameSettings.platformSpeed;

            if (player.no == 1) {
                platform.width = gameSettings.platformWidth;
                platform.height = gameSettings.platformHeight;
                platform.x = (gameSettings.gameResolution / 2) - (platform.width / 2);
                platform.y = gameSettings.gameResolution - platform.height;
            } else if (player.no == 2) {
                platform.width = gameSettings.platformWidth;
                platform.height = gameSettings.platformHeight;
                platform.x = (gameSettings.gameResolution / 2) - (platform.width / 2);
                platform.y = 0;
            } else if (player.no == 3) {
                platform.width = gameSettings.platformHeight;
                platform.height = gameSettings.platformWidth;
                platform.x = 0;
                platform.y = (gameSettings.gameResolution / 2) - (platform.height / 2);
            } else if (player.no == 4) {
                platform.width = gameSettings.platformHeight;
                platform.height = gameSettings.platformWidth;
                platform.x = gameSettings.gameResolution - platform.width;
                platform.y = (gameSettings.gameResolution / 2) - (platform.height / 2);
            }

            player.platform = platform;
        }
    }

    private void setBalls() {
        Ball ball;
        for (Player player : players) {
            for (int i = 0; i < gameSettings.ballsNo; i++) {
                ball = new Ball(player);
                ball.size = gameSettings.ballSize;
                //ball.speedX = gameSettings.ballSpeed;
                //ball.speedY = gameSettings.ballSpeed;

                ball.speedX = (random.nextInt(gameSettings.ballSpeed * 2 - 1)) + 1;
                ball.speedY = gameSettings.ballSpeed * 2 - ball.speedX;
                //ball.directionX = random.nextBoolean() ? -1 : 1;
                //ball.directionY = random.nextBoolean() ? -1 : 1;

                if (player.no == 1) {
                    if (i == 0) {
                        ball.x = (gameSettings.gameResolution / 2) - (ball.size / 2);
                    } else if (i % 2 == 0) {
                        ball.x = (gameSettings.gameResolution / 2) - (ball.size / 2) + (i * ball.size) ;
                    } else {
                        ball.x = (gameSettings.gameResolution / 2) - (ball.size / 2) - ((i + 1) * ball.size);
                    }
                    ball.y = gameSettings.gameResolution - ball.size - player.platform.height - 1;

                    ball.directionX = 1;
                    ball.directionY = -1;
                } else if (player.no == 2) {
                    if (i == 0) {
                        ball.x = (gameSettings.gameResolution / 2) - (ball.size / 2);
                    } else if (i % 2 == 0) {
                        ball.x = (gameSettings.gameResolution / 2) - (ball.size / 2) + (i * ball.size) ;
                    } else {
                        ball.x = (gameSettings.gameResolution / 2) - (ball.size / 2) - ((i + 1) * ball.size);
                    }
                    ball.y = player.platform.height + 1;

                    ball.directionX = -1;
                    ball.directionY = 1;
                } else if (player.no == 3) {
                    if (i == 0) {
                        ball.y = (gameSettings.gameResolution / 2) - (ball.size / 2);
                    } else if (i % 2 == 0) {
                        ball.y = (gameSettings.gameResolution / 2) - (ball.size / 2) + (i * ball.size) ;
                    } else {
                        ball.y = (gameSettings.gameResolution / 2) - (ball.size / 2) - ((i + 1) * ball.size);
                    }
                    ball.x = player.platform.width + 1;

                    ball.directionX = 1;
                    ball.directionY = 1;
                } else if (player.no == 4) {
                    if (i == 0) {
                        ball.y = (gameSettings.gameResolution / 2) - (ball.size / 2);
                    } else if (i % 2 == 0) {
                        ball.y = (gameSettings.gameResolution / 2) - (ball.size / 2) + (i * ball.size) ;
                    } else {
                        ball.y = (gameSettings.gameResolution / 2) - (ball.size / 2) - ((i + 1) * ball.size);
                    }
                    ball.x = gameSettings.gameResolution - player.platform.width - ball.size - 1;

                    ball.directionX = -1;
                    ball.directionY = -1;
                }

                player.balls.add(ball);
            }
        }
    }

    private void moveBalls(Player player) {
        int counterX;
        int counterY;

        Ball ball;
        for (int i = 0; i < player.balls.size(); i++) {
            ball = player.balls.get(i);
            counterX = 0;
            counterY = 0;
            do {
                if (ball.speedX > counterX) {
                    if (ball.directionX > 0) {
                        ball.x++;
                    } else if (ball.directionX < 0) {
                        ball.x--;
                    }
                    counterX++;
                }
                if (ball.speedY > counterY) {
                    if (ball.directionY > 0) {
                        ball.y++;
                    } else if (ball.directionY < 0) {
                        ball.y--;
                    }
                    counterY++;
                }
                if (ball.isAlive) {
                    detectCollision(ball);
                } else {
                    player.balls.remove(ball);
                    i--;
                    break;
                }
            } while (ball.speedX > counterX || ball.speedY > counterY);
        }
    }

    private void movePlatform(Player player) {
        Platform platform = player.platform;
        if (platform.direction != 0) {
            if (player.no == 1 || player.no == 2) {
                for (int i = 0; i < platform.speed; i++) {
                    if (platform.x + platform.direction >= 0 && platform.x + platform.width + platform.direction <= gameSettings.gameResolution()) {
                        platform.x += platform.direction;
                    } else {
                        break;
                    }
                }
            } else if (player.no == 3 || player.no == 4) {
                for (int i = 0; i < platform.speed; i++) {
                    if (platform.y + platform.direction >= 0 && platform.y + platform.height + platform.direction <= gameSettings.gameResolution()) {
                        platform.y += platform.direction;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    private void detectCollision(Ball ball) {
        boolean bc = brickCollision(ball);
        if (!ball.isAlive) { return; }
        boolean pc = platformCollision(ball);
        if (!ball.isAlive) { return; }
        boolean wc = wallCollision(ball);
    }

    private boolean brickCollision(Ball ball) {
        //make list of bricks that collided with ball and on what side
        List<Brick> collidedBricks = new ArrayList<>();
        List<String> collidedSides = new ArrayList<>();
        String side;
        for (Brick brick : bricks) {
            if (brick.isAlive) {
                if (ball.x <= brick.x + brick.width && ball.x + ball.size >= brick.x &&
                        ball.y <= brick.y + brick.height && ball.y + ball.size >= brick.y) {
                    brick.isAlive = false;
                    collidedBricks.add(brick);
                    side = "";
                    if (ball.y == brick.y + brick.height) {
                        //bottom side
                        side = "B";
                    }
                    if (ball.y + ball.size == brick.y) {
                        //top side
                        side += "T";
                    }
                    if (ball.x == brick.x + brick.width) {
                        //right side
                        side += "R";
                    }
                    if (ball.x + ball.size == brick.x) {
                        //left side
                        side += "L";
                    }
                    collidedSides.add(side);
                }
            }
        }
        if (collidedBricks.isEmpty()) { return false; }
        //calculate ball direction after colliding with bricks
        if (collidedBricks.size() == 1) {
            //1 brick
            side = collidedSides.get(0);
            if (side.length() == 2) {
                //corner
                switch (side) {
                    case "BL":
                        //bottom left corner
                        if (ball.directionX > 0 && ball.directionY < 0) {
                            //top right direction
                            ball.directionX *= -1;
                            ball.directionY *= -1;
                        } else if (ball.directionX < 0 && ball.directionY < 0) {
                            //top left direction
                            ball.directionY *= -1;
                        } else if (ball.directionX > 0 && ball.directionY > 0) {
                            //bottom right direction
                            ball.directionX *= -1;
                        }
                        break;
                    case "BR":
                        //bottom right corner
                        if (ball.directionX < 0 && ball.directionY < 0) {
                            //top left direction
                            ball.directionX *= -1;
                            ball.directionY *= -1;
                        } else if (ball.directionX > 0 && ball.directionY < 0) {
                            //top right direction
                            ball.directionY *= -1;
                        } else if (ball.directionX < 0 && ball.directionY > 0) {
                            //bottom left direction
                            ball.directionX *= -1;
                        }
                        break;
                    case "TL":
                        //top left corner
                        if (ball.directionX > 0 && ball.directionY > 0) {
                            //bottom right direction
                            ball.directionX *= -1;
                            ball.directionY *= -1;
                        } else if (ball.directionX < 0 && ball.directionY > 0) {
                            //bottom left direction
                            ball.directionX *= -1;
                        } else if (ball.directionX > 0 && ball.directionY < 0) {
                            //top right direction
                            ball.directionX *= -1;
                        }
                        break;
                    case "TR":
                        //top right corner
                        if (ball.directionX < 0 && ball.directionY > 0) {
                            //bottom left direction
                            ball.directionX *= -1;
                            ball.directionY *= -1;
                        } else if (ball.directionX > 0 && ball.directionY > 0) {
                            //bottom right direction
                            ball.directionY *= -1;
                        } else if (ball.directionX < 0 && ball.directionY < 0) {
                            //top left direction
                            ball.directionX *= -1;
                        }
                        break;
                }
            } else {
                //side
                switch (side) {
                    case "B" ->
                            //bottom side
                            ball.directionY *= -1;
                    case "T" ->
                            //top side
                            ball.directionY *= -1;
                    case "R" ->
                            //right side
                            ball.directionX *= -1;
                    case "L" ->
                            //left side
                            ball.directionX *= -1;
                }
            }
        } else if (collidedBricks.size() == 2) {
            //2 bricks
            char[] sides = (collidedSides.get(0) + collidedSides.get(1)).toCharArray();
            try {
                if (sides[0] == sides[1] || sides.length == 3) {
                    //same sides
                    if (sides[0] != sides[1]) {
                        sides[0] = sides[2];
                    }
                    switch (sides[0]) {
                        case 'B' ->
                                //bottom side
                                ball.directionY *= -1;
                        case 'T' ->
                                //top side
                                ball.directionY *= -1;
                        case 'R' ->
                                //right side
                                ball.directionX *= -1;
                        case 'L' ->
                                //left side
                                ball.directionX *= -1;
                    }
                } else {
                    //different sides or two corners
                    ball.directionX *= -1;
                    ball.directionY *= -1;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                ball.directionX *= -1;
                ball.directionY *= -1;
            }
        } else {
            //3 or more bricks
            side = "";
            for (String string : collidedSides) {
                side += string;
            }
            char[] sides = side.toCharArray();
            Arrays.sort(sides);

            boolean sameX = true;
            boolean sameY = true;
            Brick brick = collidedBricks.get(0);
            int x = brick.x;
            int y = brick.y;
            for (int i = 1; i < collidedBricks.size(); i++) {
                brick = collidedBricks.get(i);
                if (brick.x != x) {
                    sameX = false;
                }
                if (brick.y != y) {
                    sameY = false;
                }
            }

             if (sameX) {
                //same sides X
                if (sides[0] == 'R' || sides[sides.length - 1] == 'R') {
                    //right side
                    ball.directionX *= -1;
                } else if (sides[0] == 'L' || sides[sides.length - 1] == 'L') {
                    //left side
                    ball.directionX *= -1;
                }
            } else if (sameY) {
                //same sides Y
                if (sides[0] == 'B' || sides[sides.length - 1] == 'B') {
                    //bottom side
                    ball.directionY *= -1;
                } else if (sides[0] == 'T' || sides[sides.length - 1] == 'T') {
                    //top side
                    ball.directionY *= -1;
                }
            } else {
                //different sides
                ball.directionX *= -1;
                ball.directionY *= -1;
                if (collidedBricks.size() != sides.length) {
                    //with corner
                    for (int i = 0; i < collidedSides.size(); i++) {
                        if (collidedSides.get(i).length() == 2) {
                            collidedBricks.get(i).isAlive = true;
                            collidedBricks.remove(i);
                            break;
                        }
                    }
                }
            }
        }
        scorePlayerWithBricks(collidedBricks, ball.getPlayer(), ball);
        return true;
    }

    private boolean platformCollision(Ball ball) {
        Platform platform;
        for (int i = 0; i < players.size(); i++) {
            platform = players.get(i).platform;
            if (ball.x <= platform.x + platform.width && ball.x + ball.size >= platform.x &&
                    ball.y <= platform.y + platform.height && ball.y + ball.size >= platform.y) {
                if (players.get(i).no == 1) {
                    if (ball.directionY > 0 && ball.y + ball.size == platform.y) {
                        //if ball is going in platform direction and not from behind
                        if ((ball.x == platform.x + platform.width && ball.directionX < 0) ||
                                (ball.x + ball.size == platform.x && ball.directionX > 0)) {
                            //if ball hit platform corners
                            ball.directionX *= -1;
                            ball.directionY *= -1;
                            if (!ball.player.equals(players.get(i))) {
                                //if ball is from other player it gets added to platform player
                                Ball ballClone = (Ball) ball.clone();
                                ballClone.player = players.get(i);
                                players.get(i).balls.add(ballClone);
                                ball.isAlive = false;
                                if (!isGameOver) {
                                    players.get(i).score++;
                                }
                            }
                            return true;
                        } else {
                            for (int i2 = 0; i2 < ball.size; i2++) {
                                if (i2 + ball.x > platform.x && i2 + ball.x < platform.x + platform.width) {
                                    //if ball hit platform on top but not corners
                                    ball.directionY *= -1;
                                    /**int platformOneThird = platform.width / 3;
                                    int ballCenterX = ball.x + (ball.size / 2);
                                    if (ballCenterX < platform.x + platformOneThird) {
                                        //if ball center x hits left side of platform
                                        if (ball.directionX > 0) {
                                            if (ball.directionX > 1) {
                                                ball.directionY--;
                                                ball.directionX--;
                                            }
                                        }
                                    } else if (ball.directionY < -1) {
                                        {
                                            ball.directionY++;
                                            ball.directionX--;
                                        }
                                    } else if (ballCenterX > platform.x + platform.width - platformOneThird) {
                                        //if ball center x hits right side of platform
                                        if (ball.directionX > 0) {
                                            if (ball.directionY < -1) {
                                                ball.directionY++;
                                                ball.directionX++;
                                            }
                                        } else if (ball.directionX < -1) {
                                            ball.directionY--;
                                            ball.directionX++;
                                        } else {
                                            //if ball center x hits platform in center
                                        }
                                    }*/
                                    if (!ball.player.equals(players.get(i))) {
                                        //if ball is from other player it gets added to platform player
                                        Ball ballClone = (Ball) ball.clone();
                                        ballClone.player = players.get(i);
                                        players.get(i).balls.add(ballClone);
                                        ball.isAlive = false;
                                        if (!isGameOver) {
                                            players.get(i).score++;
                                        }
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                } else if (players.get(i).no == 2) {
                    if (ball.directionY < 0 && ball.y == platform.y + platform.height) {
                        //if ball is going in platform direction and not from behind
                        if ((ball.x == platform.x + platform.width && ball.directionX < 0) ||
                                (ball.x + ball.size == platform.x && ball.directionX > 0)) {
                            //if ball hit platform corners
                            ball.directionX *= -1;
                            ball.directionY *= -1;
                            if (!ball.player.equals(players.get(i))) {
                                //if ball is from other player it gets added to platform player
                                Ball ballClone = (Ball) ball.clone();
                                ballClone.player = players.get(i);
                                players.get(i).balls.add(ballClone);
                                ball.isAlive = false;
                                if (!isGameOver) {
                                    players.get(i).score++;
                                }
                            }
                            return true;
                        } else {
                            for (int i2 = 0; i2 < ball.size; i2++) {
                                if (i2 + ball.x > platform.x && i2 + ball.x < platform.x + platform.width) {
                                    //if ball hit platform on top but not corners
                                    ball.directionY *= -1;
                                    if (!ball.player.equals(players.get(i))) {
                                        //if ball is from other player it gets added to platform player
                                        Ball ballClone = (Ball) ball.clone();
                                        ballClone.player = players.get(i);
                                        players.get(i).balls.add(ballClone);
                                        ball.isAlive = false;
                                        if (!isGameOver) {
                                            players.get(i).score++;
                                        }
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                } else if (players.get(i).no == 3) {
                    if (ball.directionX < 0 && ball.x == platform.x + platform.width) {
                        //if ball is going in platform direction and not from behind
                        if ((ball.y == platform.y + platform.height && ball.directionY < 0) ||
                                (ball.y + ball.size == platform.y && ball.directionY > 0)) {
                            //if ball hit platform corners
                            ball.directionX *= -1;
                            ball.directionY *= -1;
                            if (!ball.player.equals(players.get(i))) {
                                //if ball is from other player it gets added to platform player
                                Ball ballClone = (Ball) ball.clone();
                                ballClone.player = players.get(i);
                                players.get(i).balls.add(ballClone);
                                ball.isAlive = false;
                                if (!isGameOver) {
                                    players.get(i).score++;
                                }
                            }
                            return true;
                        } else {
                            for (int i2 = 0; i2 < ball.size; i2++) {
                                if (i2 + ball.y > platform.y && i2 + ball.y < platform.y + platform.height) {
                                    //if ball hit platform on top but not corners
                                    ball.directionX *= -1;
                                    if (!ball.player.equals(players.get(i))) {
                                        //if ball is from other player it gets added to platform player
                                        Ball ballClone = (Ball) ball.clone();
                                        ballClone.player = players.get(i);
                                        players.get(i).balls.add(ballClone);
                                        ball.isAlive = false;
                                        if (!isGameOver) {
                                            players.get(i).score++;
                                        }
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                } else if (players.get(i).no == 4) {
                    if (ball.directionX > 0 && ball.x + ball.size == platform.x) {
                        //if ball is going in platform direction and not from behind
                        if ((ball.y == platform.y + platform.height && ball.directionY < 0) ||
                                (ball.y + ball.size == platform.y && ball.directionY > 0)) {
                            //if ball hit platform corners
                            ball.directionX *= -1;
                            ball.directionY *= -1;
                            if (!ball.player.equals(players.get(i))) {
                                //if ball is from other player it gets added to platform player
                                Ball ballClone = (Ball) ball.clone();
                                ballClone.player = players.get(i);
                                players.get(i).balls.add(ballClone);
                                ball.isAlive = false;
                                if (!isGameOver) {
                                    players.get(i).score++;
                                }
                            }
                            return true;
                        } else {
                            for (int i2 = 0; i2 < ball.size; i2++) {
                                if (i2 + ball.y > platform.y && i2 + ball.y < platform.y + platform.height) {
                                    //if ball hit platform on top but not corners
                                    ball.directionX *= -1;
                                    if (!ball.player.equals(players.get(i))) {
                                        //if ball is from other player it gets added to platform player
                                        Ball ballClone = (Ball) ball.clone();
                                        ballClone.player = players.get(i);
                                        players.get(i).balls.add(ballClone);
                                        ball.isAlive = false;
                                        if (!isGameOver) {
                                            players.get(i).score++;
                                        }
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean wallCollision(Ball ball) {
        boolean wc = false;
        if (ball.y == 0 || ball.y + ball.size == gameSettings.gameResolution) {
            //top and bottom
            if (ball.y + ball.size == gameSettings.gameResolution && ball.player.no == 1 ||
                    (ball.y == 0 && ball.player.no == 2)) {
                ball.isAlive = false;
            } else {
                ball.directionY *= -1;
            }
            wc = true;
        }
        if (ball.x == 0 || ball.x + ball.size == gameSettings.gameResolution) {
            //left and right
            if (ball.x == 0 && ball.player.no == 3 ||
                    (ball.x + ball.size == gameSettings.gameResolution && ball.player.no == 4)) {
                ball.isAlive = false;
            } else {
                ball.directionX *= -1;
            }
            wc = true;
        }
        if (ball.x < 0 || ball.x + ball.size > gameSettings.gameResolution ||
        ball.y + ball.size < 0 || ball.y > gameSettings.gameResolution) {
            //if ball somehow went off screen
            //ball.isAlive = false;

            if (ball.x < 0) { ball.x = 1; }
            if (ball.x + ball.size > gameSettings.gameResolution) { ball.x = gameSettings.gameResolution - ball.size - 1; }
            if (ball.y < 0) { ball.y = 1; }
            if (ball.y + ball.size > gameSettings.gameResolution) { ball.y = gameSettings.gameResolution - ball.size - 1; }

            return false;
        }
        return wc;
    }

    private void scorePlayerWithBricks(List<Brick> collidedBricks, Player player, Ball ball) {
        Ball ballClone;
        for (Brick brick : collidedBricks) {
            switch (brick.type) {
                case STANDARD:
                    player.score++;
                    break;
                case PLUS:
                    player.score += 5;
                    break;
                case MINUS:
                    player.score -= 5;
                    break;
                case BALL_1X:
                    player.score++;
                    ballClone = (Ball) ball.clone();
                    ballClone.directionX *= -1;
                    ballClone.directionY *= -1;
                    player.balls.add(ballClone);
                    break;
                case BALL_2X:
                    player.score++;
                    int counter = player.balls.size();
                    for (int i = 0; i < counter; i++) {
                        ballClone = (Ball) player.balls.get(i).clone();
                        ballClone.directionX *= -1;
                        ballClone.directionY *= -1;
                        player.balls.add(ballClone);
                    }
                    break;
                case DEATH:
                    player.score--;
                    for (int i = 0; i < player.balls.size(); i++) {
                        if (!player.balls.get(i).equals(ball)) {
                            player.balls.get(i).isAlive = false;
                        }
                    }
                    break;
                case BALL_SIZE_PLUS:
                    player.score++;
                    for (int i = 0; i < player.balls.size(); i++) {
                        player.balls.get(i).size += 2;
                    }
                    break;
                case BALL_SIZE_MINUS:
                    player.score++;
                    for (int i = 0; i < player.balls.size(); i++) {
                        if (player.balls.get(i).size > 2) {
                            player.balls.get(i).size -= 2;
                        } else if (player.balls.get(i).size == 2) {
                            player.balls.get(i).size--;
                        }
                    }
                    break;
                case BALL_SPEED_PLUS:
                    player.score++;
                    for (int i = 0; i < player.balls.size(); i++) {
                        player.balls.get(i).speedX++;
                        player.balls.get(i).speedY++;
                    }
                    break;
                case BALL_SPEED_MINUS:
                    player.score++;
                    for (int i = 0; i < player.balls.size(); i++) {
                        if (player.balls.get(i).speedX > 1 && player.balls.get(i).speedY > 1) {
                            player.balls.get(i).speedX--;
                            player.balls.get(i).speedY--;
                        }
                    }
                    break;
                case PLATFORM_SIZE_PLUS:
                    player.score++;
                        switch (player.no) {
                            case 1:
                            case 2:
                                player.platform.width += 50;
                                player.platform.x -= 25;

                                if (player.platform.x < 0) {
                                    player.platform.x = 0;
                                } else if (player.platform.x + player.platform.width > gameSettings.gameResolution) {
                                    player.platform.x = gameSettings.gameResolution - player.platform.width;
                                }
                                break;
                            case 3:
                            case 4:
                                player.platform.height += 50;
                                player.platform.y -= 25;
                                if (player.platform.y < 0) {
                                    player.platform.y = 0;
                                } else if (player.platform.y + player.platform.height > gameSettings.gameResolution) {
                                    player.platform.y = gameSettings.gameResolution - player.platform.height;
                                }
                                break;
                        }
                    break;
                case PLATFORM_SIZE_MINUS:
                    player.score++;
                    switch (player.no) {
                        case 1:
                            player.platform.width -= 50;
                            player.platform.x += 25;
                            break;
                        case 2:
                            player.platform.width -= 50;
                            player.platform.x += 25;
                            break;
                        case 3:
                            player.platform.height -= 50;
                            player.platform.y += 25;
                            break;
                        case 4:
                            player.platform.height -= 50;
                            player.platform.y += 25;
                            break;
                    }
                    break;
                case PLATFORM_SPEED_PLUS:
                    player.score++;
                    player.platform.speed += 2;
                    break;
                case PLATFORM_SPEED_MINUS:
                    player.score++;
                    if (player.platform.speed > 2) {
                        player.platform.speed -= 2;
                    } else if (player.platform.speed == 2) {
                        player.platform.speed--;
                    }
                    break;
            }
        }
    }

    private void checkIsGameOver() {
        boolean isBrickAlive = false;
        boolean isBallAlive = false;
        for (Brick brick : bricks) {
            if (brick.isAlive) {
                isBrickAlive = true;
                break;
            }
        }
        for (Player player : players) {
            for (Ball ball : player.balls) {
                if (ball.isAlive) {
                    isBallAlive = true;
                    break;
                }
            }
        }
        isGameOver = !isBrickAlive || !isBallAlive;
    }

    @Override
    public void timerPerformed(Timer timer) {
        //null check is for debugging use, remove later
        if (isUpdating) { return; }
        isUpdating = true;
        if (this.timer == timer || timer == null) {
            for (Player player : players) {
                moveBalls(player);
                movePlatform(player);
                checkIsGameOver();
                bot.play();
                if (timerListener != null) {
                    timerListener.timerPerformed(timer);
                }
            }
        }
        isUpdating = false;
    }

    public GameSettings getSettings() {
        return gameSettings;
    }

    public int getBricksSize() {
        return bricks.size();
    }

    public Brick getBrick(int i) {
        return bricks.get(i);
    }

    public int getPlayersSize() {
        return players.size();
    }

    public Player getPlayer(int i) {
        return players.get(i);
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
