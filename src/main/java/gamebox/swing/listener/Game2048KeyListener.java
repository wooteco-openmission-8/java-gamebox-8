package gamebox.swing.listener;

import gamebox.game_2048.controller.Game2048Controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game2048KeyListener extends KeyAdapter {
    private final Game2048Controller controller;
    private final Runnable onMoved;

    public Game2048KeyListener(Game2048Controller controller, Runnable onMoved) {
        this.controller = controller;
        this.onMoved = onMoved;
    }

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
            onMoved.run();
        }
    }
}
