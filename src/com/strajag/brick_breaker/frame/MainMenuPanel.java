package com.strajag.brick_breaker.frame;

import com.strajag.brick_breaker.game.Game;
import com.strajag.brick_breaker.game.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainMenuPanel extends JPanel implements ActionListener {

    GameSettings gameSettings;

    GridBagConstraints gbc = new GridBagConstraints();

    JButton startButton1 = new JButton("Start Mode 1");
    JButton startButton2 = new JButton("Start Mode 2");
    JButton exitButton1 = new JButton("EXIT");

    MainMenuPanel(GameSettings gameSettings) {
        this.gameSettings = gameSettings;

        setPreferredSize(new Dimension(gameSettings.gameResolution(), gameSettings.gameResolution()));
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        setCursor(Cursor.getDefaultCursor());

        gbc.insets = new Insets(10, 0,10, 0);
        gbc.gridy = 0;
        add(startButton1, gbc);
        gbc.gridy = 1;
        add(startButton2, gbc);
        gbc.gridy = 2;
        add(exitButton1, gbc);

        startButton1.addActionListener(this);
        startButton2.addActionListener(this);
        exitButton1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JButton pressedButton = (JButton) actionEvent.getSource();

        if (pressedButton.equals(startButton1)) {
            //settings.setGameMode(0);
            com.strajag.brick_breaker.frame.Frame.setPanel(new GamePanel(new Game(gameSettings)));
        } else if (pressedButton.equals(startButton2)) {
            //settings.setGameMode(1);
            Frame.setPanel(new GamePanel(new Game(gameSettings)));
        } else if (pressedButton.equals(exitButton1)) {
            System.exit(1);
        }
    }
}
