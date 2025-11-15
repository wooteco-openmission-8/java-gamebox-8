package gamebox.swing.panel;

import gamebox.swing.swing_util.SwingUtils;

import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    private static final int ROW_COUNT = 3;
    private static final int COL_COUNT = 1;

    private final JLabel title = new JLabel("GameBox");
    private final JLabel selectGame = new JLabel("게임을 선택하세요.");
    private final JButton homeButton = new JButton("홈버튼");

    public BackgroundPanel(){
        setBackground(Color.white);
        setLayout(new GridLayout(ROW_COUNT, COL_COUNT));
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
        SwingUtils.refresh(this);
    }
}
