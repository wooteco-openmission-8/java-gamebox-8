package gamebox.view.components;

import gamebox.view.gameScreen.Picture;

import javax.swing.*;
import java.awt.*;

public class Grid {
    public static JPanel createGridPanel(int rows, int cols) {
        JPanel panel = new JPanel(new GridLayout(rows, cols, rows, cols)); // (행, 열, 가로간격, 세로간격)
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }
}
