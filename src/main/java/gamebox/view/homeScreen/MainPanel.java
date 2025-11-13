package gamebox.view.homeScreen;

import gamebox.controller.GameBoxListener;

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
        GameBoxListener listener = new GameBoxListener(contentPanel,backgroundPanel);
        gameButtonPanel.addGameButtonListener(listener);
        backgroundPanel.addHomeButtonListener(listener);
    }

    public void setContent(JPanel newContent) {
        contentPanel.removeAll();
        contentPanel.add(newContent, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
