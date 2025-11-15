package gamebox.swing.panel;

import gamebox.game_2048.controller.Game2048Controller;
import gamebox.game_2048.entity.Tile;
import gamebox.game_2048.entity.GameStatus;
import gamebox.game_samepic.game.controller.GameSamePicController;
import gamebox.swing.components.TilePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game2048Panel extends JPanel {
    private static final int GRID_GAP = 10;
    private static final int GRID_SIZE = 4;

    private final JPanel resetPanel = new JPanel();
    private final JPanel gamePanel = new JPanel();

    private TilePanel[][] tilePanels;
    private final Game2048Controller controller;

    public Game2048Panel() {
        this.controller = new Game2048Controller();

        setLayout(new BorderLayout());
        setFocusable(false);

        setResetPanel();
        setGamePanel();

        add(resetPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        controller.start();  // Service 초기화
        updateBoard();
    }

    private void drawTiles() {
        gamePanel.removeAll();
        tilePanels = new TilePanel[GRID_SIZE][GRID_SIZE];

        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                TilePanel tilePanel = new TilePanel();
                Tile t = controller.getTile(r, c);

                tilePanel.setTile(t.getNumber(), t.getTextColor(), t.getBackgroundColor());

                tilePanels[r][c] = tilePanel;
                gamePanel.add(tilePanel);
            }
        }

        revalidate();
        repaint();
    }

    public void updateBoard() {
        drawTiles();
        checkGameStatus();
    }

    private void checkGameStatus() {
        GameStatus status = controller.getGameStatus();
        if (status == GameStatus.WIN) {
            JOptionPane.showMessageDialog(this, "You WIN!");
        } else if (status == GameStatus.GAME_OVER) {
            JOptionPane.showMessageDialog(this, "Game Over!");
        }
    }

    private void setResetPanel() {
        resetPanel.setBackground(Color.WHITE);
        JButton resetButton = new JButton("Reset");
        resetButton.setFocusable(false);
        resetButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "게임을 초기화하시겠습니까?",
                    "확인",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                controller.start();  // Service 재초기화
                updateBoard();
                SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
            }
        });
        resetPanel.add(resetButton);
    }

    private void setGamePanel() {
        gamePanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE, GRID_GAP, GRID_GAP));
        gamePanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        gamePanel.setBackground(Color.WHITE);

        addKeyListenerToPanel();

        gamePanel.setFocusable(true);
        gamePanel.setFocusTraversalKeysEnabled(false);

        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
    }

    private void addKeyListenerToPanel() {
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean moved = false;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> moved = controller.moveUp();
                    case KeyEvent.VK_DOWN -> moved = controller.moveDown();
                    case KeyEvent.VK_LEFT -> moved = controller.moveLeft();
                    case KeyEvent.VK_RIGHT -> moved = controller.moveRight();
                }

                if (moved) {
                    updateBoard();
                }
            }
        });
    }
}
