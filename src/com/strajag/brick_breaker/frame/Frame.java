package com.strajag.brick_breaker.frame;

import com.strajag.brick_breaker.game.Game;
import com.strajag.brick_breaker.game.GameSettings;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    static JPanel framePanel;

    public Frame(FrameSettings frameSettings, Game game) {
        setTitle("brick_breaker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        //setUndecorated(true);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        framePanel = new JPanel(new GridBagLayout());
        framePanel.setPreferredSize(new Dimension(game.getSettings().gameResolution(), game.getSettings().gameResolution()));
        add(framePanel);
        pack();
        setPanel(new GamePanel(game));
        setLocationRelativeTo(null);
        setVisible(true);
        /*hide cursor
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");
        setCursor(blankCursor);*/
    }

    static void setPanel(JPanel jPanel) {
        framePanel.removeAll();
        framePanel.add(jPanel);
        framePanel.revalidate();
        framePanel.repaint();
    }
}
