package gamebox.view.homeScreen;

import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private final JLabel title = new JLabel("GameBox");
    private final JLabel selectGame = new JLabel("게임을 선택하세요.");
    private final JButton homeButton = new JButton("홈버튼");

    public BackgroundPanel(){
        setBackground(Color.white);
        setLayout(new GridLayout(3, 1));
        selectGame.setVisible(true);
        homeButton.setVisible(false);
        alignTexts();
        addComponents();
    }

    private void alignTexts(){
        title.setHorizontalAlignment(JLabel.CENTER);
        selectGame.setHorizontalAlignment(JLabel.CENTER);
        homeButton.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void addComponents(){
        add(title);
        add(selectGame);
        add(homeButton);
    }

    public void addHomeButtonListener(ActionListener listener) {
        homeButton.addActionListener(listener);
    }

    public void showHomeButton(boolean show) {
        homeButton.setVisible(show);
        selectGame.setVisible(!show);
        revalidate();
        repaint();
    }
}
