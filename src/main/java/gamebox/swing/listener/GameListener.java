package gamebox.swing.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameListener implements ActionListener {
    private final Component parent;
    private final String message;
    private final String clicked;
    private final Runnable onClick;

    public GameListener(Component parent, String message, String clicked, Runnable onClick) {
        this.parent = parent;
        this.message = message;
        this.clicked = clicked;
        this.onClick = onClick;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(
                parent,
                message,
                clicked,
                JOptionPane.YES_NO_OPTION
        );
        if (choice == JOptionPane.YES_OPTION) {
            onClick.run();
        }
    }
}
