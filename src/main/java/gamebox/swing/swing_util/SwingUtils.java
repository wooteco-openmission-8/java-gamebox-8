package gamebox.swing.swing_util;

import javax.swing.*;

public class SwingUtils {
    private SwingUtils() {

    }

    public static void refresh(JComponent component) {
        component.revalidate();
        component.repaint();
    }
}
