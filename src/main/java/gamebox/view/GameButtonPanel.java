package gamebox.view;

import javax.swing.*;
import java.awt.*;

public class GameButtonPanel extends JPanel{
    private static final JButton game2048Button = new JButton("2048");
    private static final JButton gameSamePicButton = new JButton("같은 그림 찾기");

    public GameButtonPanel(){
        setBackground(Color.white);
        setLayout(null);
        setButtonSize();
        setButtonLocation();
        addComponents();
    }

    private void setButtonSize(){
        game2048Button.setSize(150, 80);
        gameSamePicButton.setSize(150, 80);
    }

    private void setButtonLocation(){
        game2048Button.setLocation(425, 0);
        gameSamePicButton.setLocation(425, 100);
    }

    private void addComponents(){
        add(game2048Button);
        add(gameSamePicButton);
    }
}
