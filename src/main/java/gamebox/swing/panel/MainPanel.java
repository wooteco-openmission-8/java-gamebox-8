package gamebox.swing.panel;

import gamebox.swing.listener.AppListener;
import gamebox.swing.swing_util.SwingUtils;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private final JPanel contentPanel = new JPanel(new BorderLayout());
    private final BackgroundPanel backgroundPanel = new BackgroundPanel();
    private final GameButtonPanel gameButtonPanel = new GameButtonPanel();

    public MainPanel() {
        setLayout(new BorderLayout());
        addPanels();
        addListeners();
        setInitialContent();
    }

    private void setInitialContent() {
        setContent(gameButtonPanel);
    }

    private void addPanels() {
        add(backgroundPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void addListeners() {
        AppListener listener = new AppListener(contentPanel,backgroundPanel);
        gameButtonPanel.addGameButtonListener(listener);
        backgroundPanel.addHomeButtonListener(listener);
    }

    public void setContent(JPanel newContent) {
        contentPanel.removeAll();
        contentPanel.add(newContent, BorderLayout.CENTER);
        SwingUtils.refresh(contentPanel);
    }
}
