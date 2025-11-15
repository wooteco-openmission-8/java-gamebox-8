package gamebox.swing.listener;

import gamebox.swing.panel.Game2048Panel;
import gamebox.swing.panel.GameSamePicPanel;
import gamebox.swing.panel.BackgroundPanel;
import gamebox.swing.panel.GameButtonPanel;
import gamebox.swing.swing_util.SwingUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppListener implements ActionListener {
    private static final String GAME_2048_BUTTON_NAME = "2048";
    private static final String GAME_SAME_PIC_BUTTON_NAME = "같은 그림 찾기";
    private static final String HOME_BUTTON_NAME = "홈버튼";

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

        if (selectedButton.equals(GAME_2048_BUTTON_NAME)) {
            openGame2048();
        }
        if (selectedButton.equals(GAME_SAME_PIC_BUTTON_NAME)) {
            openGameSamePic();
        }
        if (selectedButton.equals(HOME_BUTTON_NAME)) {
            goBackToHome();
        }

        SwingUtils.refresh(contentPanel);
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
