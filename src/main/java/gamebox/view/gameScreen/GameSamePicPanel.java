package gamebox.view.gameScreen;

import javax.swing.*;
import java.awt.*;

public class GameSamePicPanel extends JPanel {
    public GameSamePicPanel(){
        setOpaque(true);
        setLayout(new BorderLayout());
        add(new JLabel("Game 같은 그림 찾기", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
