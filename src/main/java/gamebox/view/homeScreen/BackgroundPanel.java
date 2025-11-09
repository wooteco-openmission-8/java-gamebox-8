package gamebox.view.homeScreen;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private static final JLabel title = new JLabel("GameBox");
    private static final JLabel selectGame = new JLabel("게임을 선택하세요.");

    public BackgroundPanel(){
        setBackground(Color.white);
        setLayout(new GridLayout(2, 1));
        alignTexts();
        addComponents();
    }

    private void alignTexts(){
        title.setHorizontalAlignment(JLabel.CENTER);
        selectGame.setHorizontalAlignment(JLabel.CENTER);
    }

    private void addComponents(){
        add(title);
        add(selectGame);
    }
}
