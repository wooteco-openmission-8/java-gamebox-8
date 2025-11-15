package gamebox.swing.listener;

import gamebox.swing.panel.Game2048Panel;
import gamebox.swing.panel.GameSamePicPanel;
import gamebox.swing.panel.BackgroundPanel;
import gamebox.swing.panel.GameButtonPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppListener implements ActionListener {
    private final JPanel contentPanel;
    private final BackgroundPanel backgroundPanel; // 추가

    public AppListener(JPanel contentPanel, BackgroundPanel backgroundPanel){
        this.contentPanel = contentPanel;
        this.backgroundPanel = backgroundPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        String selectedButton = source.getText();

        if (selectedButton.equals("2048")) {
            openGame2048();
        }
        if (selectedButton.equals("같은 그림 찾기")) {
            openGameSamePic();
        }
        if (selectedButton.equals("홈버튼")) {
            goBackToHome();
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void openGame2048() {
        contentPanel.removeAll();
        contentPanel.add(new Game2048Panel());
        backgroundPanel.showHomeButton(true); // 홈버튼 보이기, selectGame 숨김
    }

    private void openGameSamePic() {
        contentPanel.removeAll();
        contentPanel.add(new GameSamePicPanel());
        backgroundPanel.showHomeButton(true);
    }

    private void goBackToHome() {
        contentPanel.removeAll();
        contentPanel.add(new GameButtonPanel());
        backgroundPanel.showHomeButton(false);
    }
}
