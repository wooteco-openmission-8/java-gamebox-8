package gamebox.swing.components;


import javax.swing.*;
import java.awt.*;

public class Grid {
    private static final int PADDING_SIZE = 10;

    public static JPanel createGridPanel(int rows, int cols) {
        JPanel panel = new JPanel(new GridLayout(rows, cols, rows, cols)); // (행, 열, 가로간격, 세로간격)
        panel.setBorder(BorderFactory.createEmptyBorder(
                PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE
        ));
        return panel;
    }
}
