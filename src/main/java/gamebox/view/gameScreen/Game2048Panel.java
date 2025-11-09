package gamebox.view.gameScreen;

import javax.swing.*;
import java.awt.*;

public class Game2048Panel extends JLabel {
    public Game2048Panel(){
        setOpaque(true);
        setLayout(new BorderLayout());
        add(new JLabel("Game 2048", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
