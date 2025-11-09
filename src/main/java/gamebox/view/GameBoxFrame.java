package gamebox.view;

import javax.swing.*;
import java.awt.*;

public class GameBoxFrame extends JFrame {
    private static final BackgroundPanel backGroundPanel = new BackgroundPanel();
    private static final GameButtonPanel gameSelectButtonPanel = new GameButtonPanel();

    public GameBoxFrame(){
        setInit();
        setLayout(new GridLayout(2, 1));
        addPanels();
    }

    private void setInit(){
        setSize(1000, 800);
        setResizable(false);
        setBackground(Color.white);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addPanels(){
        add(backGroundPanel);
        add(gameSelectButtonPanel);
    }
}
