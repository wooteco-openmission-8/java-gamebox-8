package gamebox.view.homeScreen;

import gamebox.controller.GameBoxListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameBoxFrame extends JFrame {
    private final JPanel contentPanel = new JPanel();
    private static final BackgroundPanel backGroundPanel = new BackgroundPanel();
    private static final GameButtonPanel gameSelectButtonPanel = new GameButtonPanel();

    public GameBoxFrame(){
        setInit();
        addGameButtonListener();
        addPanels();
    }

    private void setInit(){
        setSize(1000, 800);
        setResizable(false);
        setBackground(Color.white);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addGameButtonListener(){
        gameSelectButtonPanel.addGameButtonListener(new GameBoxListener(contentPanel) {
        });
    }

    private void addPanels(){
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(backGroundPanel, BorderLayout.NORTH);
        contentPanel.add(gameSelectButtonPanel, BorderLayout.CENTER);
        add(contentPanel);
    }
}
