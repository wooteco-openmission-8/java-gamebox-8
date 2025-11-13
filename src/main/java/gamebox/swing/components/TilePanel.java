package gamebox.swing.components;

import javax.swing.*;
import java.awt.*;

public class TilePanel extends JPanel {
    private static final int TILE_SIZE = 1;

    private JLabel label;

    public TilePanel() {
        setLayout(new GridLayout(TILE_SIZE, TILE_SIZE));
        label = new JLabel("", SwingConstants.CENTER);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label);
    }

    public void setTile(int number, Color textColor, Color backgroundColor) {
        label.setText(number == 0 ? "" : String.valueOf(number));
        label.setForeground(textColor);
        setBackground(backgroundColor);
        repaint();
    }
}
