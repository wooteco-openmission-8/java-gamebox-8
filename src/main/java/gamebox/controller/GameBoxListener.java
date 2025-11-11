package gamebox.controller;

import gamebox.view.gameScreen.Game2048Panel;
import gamebox.view.gameScreen.GameSamePicPanel;
import gamebox.view.homeScreen.BackgroundPanel;
import gamebox.view.homeScreen.GameButtonPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoxListener implements ActionListener {
    private final JPanel contentPanel;
    private final BackgroundPanel backgroundPanel; // 추가

    public GameBoxListener(JPanel contentPanel, BackgroundPanel backgroundPanel){
        this.contentPanel = contentPanel;
        this.backgroundPanel = backgroundPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.getText().equals("2048")) {
            System.out.println("2048 클릭");
            contentPanel.removeAll();
            contentPanel.add(new Game2048Panel());
            backgroundPanel.showHomeButton(true); // 홈버튼 보이기, selectGame 숨김
        }

        if (source.getText().equals("같은 그림 찾기")) {
            System.out.println("같은 그림 찾기 클릭");
            contentPanel.removeAll();
            contentPanel.add(new GameSamePicPanel());
            backgroundPanel.showHomeButton(true);
        }

        if (source.getText().equals("홈버튼")) {
            System.out.println("홈버튼 클릭");
            contentPanel.removeAll();
            contentPanel.add(new GameButtonPanel());
            backgroundPanel.showHomeButton(false);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
