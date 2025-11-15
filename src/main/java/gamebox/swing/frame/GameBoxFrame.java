package gamebox.swing.frame;

import gamebox.swing.panel.MainPanel;
import javax.swing.*;
import java.awt.*;

public class GameBoxFrame extends JFrame {
    private static final String APP_NAME = "GameBox";

    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 800;

    private final MainPanel mainPanel = new MainPanel();

    public GameBoxFrame(){
        setInit();
        add(mainPanel);
        setVisible(true);
    }

    private void setInit(){
        setTitle(APP_NAME);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setBackground(Color.white);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}