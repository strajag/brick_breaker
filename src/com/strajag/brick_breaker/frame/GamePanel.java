package com.strajag.brick_breaker.frame;

import com.strajag.brick_breaker.game.*;
import com.strajag.brick_breaker.tools.Timer;
import com.strajag.brick_breaker.tools.TimerListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

class GamePanel extends JPanel implements TimerListener, KeyListener {

    Game game;
    GameSettings gameSettings;
    List<Integer> keysPressed = new ArrayList<>();
    Random random = new Random();

    HashMap<BrickType, BufferedImage> hashMap = new HashMap<>();

    GamePanel(Game game) {
        this.game = game;
        gameSettings = game.getSettings();
        game.setTimerListener(this);

        setPreferredSize(new Dimension(gameSettings.gameResolution(), gameSettings.gameResolution()));
        setBackground(Color.BLACK);
        setFocusable(true);

        addKeyListener(this);

        try {
            BufferedImage image = ImageIO.read(new File("data/images/bricks/STANDARD.png"));
            hashMap.put(BrickType.STANDARD, image);
            image = ImageIO.read(new File("data/images/bricks/BALL_2X.png"));
            hashMap.put(BrickType.BALL_2X, image);
            image = ImageIO.read(new File("data/images/bricks/DEATH.png"));
            hashMap.put(BrickType.DEATH, image);

        } catch (Exception e) { e.printStackTrace(); }
    }

    void draw(Graphics graphics) {
        //draw grid
        /**int startPosition = 0;
        if (settings.isBricksInCenter()) {
            startPosition = settings.getGameResolution() / 4;
        }
        //draw x line for wall grid
        for (int i = 0; i < settings.getRows() + 1; i++) {
            graphics.drawLine(
                    startPosition,
                    i * settings.getGridHeight() + startPosition,
                    settings.getGameResolution() - startPosition,
                    i * settings.getGridHeight() + startPosition);
        }
        //draw y line for wall grid
        for (int i = 0; i < settings.getColumns() + 1; i++) {
            graphics.drawLine(
                    i * settings.getGridWidth() + startPosition,
                    startPosition,
                    i * settings.getGridWidth() + startPosition,
                    settings.getGridHeight() * settings.getRows() + startPosition);
        }*/
        Brick brick;
        graphics.setFont(new Font("Monospace", Font.PLAIN, (gameSettings.getGridWidth() / 3)));
        FontMetrics fontMetrics = getFontMetrics(graphics.getFont());
        //draw bricks
        //graphics.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < game.getBricksSize(); i++) {
            brick = game.getBrick(i);
            if (brick.isAlive()) {
                //draw image
                if (hashMap.containsKey(brick.getType())) {
                    graphics.drawImage(hashMap.get(brick.getType()), brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight(), null);
                } else {
                    //graphics.setColor(Color.LIGHT_GRAY);
                    //graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    graphics.setColor(brick.getColor());
                    graphics.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());

                    //draw brick numbers
                    //graphics.setColor(Color.RED);
                    //graphics.drawString(i+1 + "", brick.getX() + ((brick.getWidth() - fontMetrics.stringWidth(i+1 + "")) / 2), brick.getY() + (brick.getHeight() - fontMetrics.getHeight() + fontMetrics.getAscent()));
                    //draw brick type
                    graphics.setColor(brick.getColor().darker());
                    graphics.drawString(brick.getType().toString().substring(0, 4), brick.getX() + ((brick.getWidth() - fontMetrics.stringWidth(brick.getType().toString().substring(0, 4))) / 2), brick.getY() + (brick.getHeight() - fontMetrics.getHeight() + fontMetrics.getAscent()));
                }
            }
        }
        for (int i = 0; i < game.getPlayersSize(); i++) {
            //draw platforms
            Platform platform = game.getPlayer(i).getPlatform();
            graphics.setColor(game.getPlayer(i).getColor());
            graphics.fillRect(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
            //draw balls
            for (int i2 = 0; i2 < game.getPlayer(i).getBallsSize(); i2++) {
                Ball ball = game.getPlayer(i).getBall(i2);
                if (ball.isAlive()) {
                    graphics.setColor(game.getPlayer(i).getColor());
                    graphics.fillOval(ball.getX(), ball.getY(), ball.getSize(), ball.getSize());
                    //graphics.fillRect(ball.getX(), ball.getY(), ball.getSize(), ball.getSize());
                    //draw ball numbers
                    //graphics.setColor(Color.BLACK);
                    //graphics.drawString(game.getPlayer(i).getNo() + ":" + (i2 + 1), ball.getX() + ((ball.getSize() - fontMetrics.stringWidth(game.getPlayer(i).getNo() + ":" + (i2 + 1))) / 2), ball.getY() + (ball.getSize() - fontMetrics.getHeight() + fontMetrics.getAscent()));
                }
            }
        }
        //score text
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Ink Free", Font.BOLD, gameSettings.getGameResolution() / 20));
        FontMetrics fontMetrics2 = getFontMetrics(graphics.getFont());
        graphics.drawString(game.getPlayer(0).getScore() + "p",
                (gameSettings.getGameResolution() - fontMetrics2.stringWidth(game.getPlayer(0).getScore() + "p")) / 2,
                gameSettings.getGameResolution() - fontMetrics2.getHeight() - 30);

        if (game.getPlayersSize() > 1) {
            graphics.drawString(game.getPlayer(1).getScore() + "p",
                    (gameSettings.getGameResolution() - fontMetrics2.stringWidth(game.getPlayer(1).getScore() + "p")) / 2,
                    fontMetrics2.getHeight() + 60);
        }

        if (game.getPlayersSize() > 2) {
            graphics.drawString(game.getPlayer(2).getScore() + "p",
                    (gameSettings.getGameResolution() / 4 - fontMetrics2.stringWidth(game.getPlayer(2).getScore() + "p")) / 2,
                    gameSettings.getGameResolution() / 2);
        }

        if (game.getPlayersSize() > 3) {
            graphics.drawString(game.getPlayer(3).getScore() + "p",
                    gameSettings.getGameResolution() - fontMetrics2.stringWidth(game.getPlayer(3).getScore() + "p") * 2 - fontMetrics2.getHeight() / 3,
                    gameSettings.getGameResolution() / 2);
        }
        if (game.isGameOver()) {
            gameOver(graphics);
        }
    }

    void gameOver(Graphics graphics) {
        //game over text
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Ink Free", Font.BOLD, gameSettings.getGameResolution() / 11));
        FontMetrics fontMetrics1 = getFontMetrics(graphics.getFont());
        graphics.drawString("GAME OVER", (gameSettings.gameResolution() - fontMetrics1.stringWidth("GAME OVER")) / 2, gameSettings.gameResolution() / 2);


    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        /*this code can scale graphics, can be used if frame is resized
        Graphics2D g2 = (Graphics2D) graphics;
        g2.scale(2.0, 2.0);*/

        draw(graphics);
    }

    @Override
    public void timerPerformed(Timer timer) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (!keysPressed.contains(KeyEvent.VK_LEFT)) {
                    keysPressed.add(KeyEvent.VK_LEFT);
                    game.getPlayer(0).setPlatformDirection(-1);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!keysPressed.contains(KeyEvent.VK_RIGHT)) {
                    keysPressed.add(KeyEvent.VK_RIGHT);
                    game.getPlayer(0).setPlatformDirection(+1);
                }
                break;
            case KeyEvent.VK_A:
                if (game.getPlayersSize() > 1) {
                    if (!keysPressed.contains(KeyEvent.VK_A)) {
                        keysPressed.add(KeyEvent.VK_A);
                        game.getPlayer(1).setPlatformDirection(-1);
                    }
                }
                break;
            case KeyEvent.VK_D:
                if (game.getPlayersSize() > 1) {
                    if (!keysPressed.contains(KeyEvent.VK_D)) {
                        keysPressed.add(KeyEvent.VK_D);
                        game.getPlayer(1).setPlatformDirection(+1);
                    }
                }
                break;
            case KeyEvent.VK_H:
                if (game.getPlayersSize() > 2) {
                    if (!keysPressed.contains(KeyEvent.VK_H)) {
                        keysPressed.add(KeyEvent.VK_H);
                        game.getPlayer(2).setPlatformDirection(-1);
                    }
                }
                break;
            case KeyEvent.VK_N:
                if (game.getPlayersSize() > 2) {
                    if (!keysPressed.contains(KeyEvent.VK_N)) {
                        keysPressed.add(KeyEvent.VK_N);
                        game.getPlayer(2).setPlatformDirection(+1);
                    }
                }
                break;
            case KeyEvent.VK_NUMPAD8:
                if (game.getPlayersSize() > 3) {
                    if (!keysPressed.contains(KeyEvent.VK_NUMPAD8)) {
                        keysPressed.add(KeyEvent.VK_NUMPAD8);
                        game.getPlayer(3).setPlatformDirection(-1);
                    }
                }
                break;
            case KeyEvent.VK_NUMPAD2:
                if (game.getPlayersSize() > 3) {
                    if (!keysPressed.contains(KeyEvent.VK_NUMPAD2)) {
                        keysPressed.add(KeyEvent.VK_NUMPAD2);
                        game.getPlayer(3).setPlatformDirection(+1);
                    }
                }
                break;
            case KeyEvent.VK_P:
                game.start();
                break;
            case KeyEvent.VK_R:
                game.restart();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
                //only for debugging, delete all cases below later
            case KeyEvent.VK_M:
                for (int i = 0; i < game.getPlayersSize(); i++) {
                    game.timerPerformed(null);
                }
                break;
            case KeyEvent.VK_U:
                game.getPlayer(0).getBall(0).directionX *= -1;
                repaint();
                break;
            case KeyEvent.VK_O:
                game.getPlayer(0).getBall(0).directionY *= -1;
                repaint();
                break;
            case KeyEvent.VK_I:
                game.getPlayer(0).getBall(0).y--;
                repaint();
                break;
            case KeyEvent.VK_K:
                game.getPlayer(0).getBall(0).y++;
                repaint();
                break;
            case KeyEvent.VK_J:
                game.getPlayer(0).getBall(0).x--;
                repaint();
                break;
            case KeyEvent.VK_L:
                game.getPlayer(0).getBall(0).x++;
                repaint();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(Integer.valueOf(e.getKeyCode()));
        if (!keysPressed.contains(KeyEvent.VK_LEFT) && !keysPressed.contains(KeyEvent.VK_RIGHT)) {
            game.getPlayer(0).setPlatformDirection(0);
        }
        if (game.getPlayersSize() > 1 && !keysPressed.contains(KeyEvent.VK_A) && !keysPressed.contains(KeyEvent.VK_D)) {
            game.getPlayer(1).setPlatformDirection(0);
        }
        if (game.getPlayersSize() > 2 && !keysPressed.contains(KeyEvent.VK_H) && !keysPressed.contains(KeyEvent.VK_N)) {
            game.getPlayer(2).setPlatformDirection(0);
        }
        if (game.getPlayersSize() > 3 && !keysPressed.contains(KeyEvent.VK_NUMPAD8) && !keysPressed.contains(KeyEvent.VK_NUMPAD2)) {
            game.getPlayer(3).setPlatformDirection(0);
        }
    }
}
