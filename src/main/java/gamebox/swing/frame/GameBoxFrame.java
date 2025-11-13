package gamebox.swing.frame;

import gamebox.swing.panel.MainPanel;
import javax.swing.*;
import java.awt.*;

public class GameBoxFrame extends JFrame {
    private final MainPanel mainPanel = new MainPanel();

    public GameBoxFrame(){
        setInit();
        add(mainPanel);
        setVisible(true);
    }

    private void setInit(){
        setTitle("GameBox");
        setSize(1000, 800);
        setResizable(false);
        setBackground(Color.white);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}