package gamebox.swing.listener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameListener implements ActionListener {
    private static final String GO_BACK_TO_SELECT_DIFFICULTY = "난이도 선택 화면으로 돌아가시겠습니까?\n현재 게임이 초기화됩니다.";
    private static final String YES = "확인";

    private final Component parent;
    private final Runnable onClick;

    public GameListener(Component parent, Runnable onClick) {
        this.parent = parent;
        this.onClick = onClick;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int choice = JOptionPane.showConfirmDialog(
                this.parent,
                GO_BACK_TO_SELECT_DIFFICULTY,
                YES,
                JOptionPane.YES_NO_OPTION
        );
        if (choice == JOptionPane.YES_OPTION) {
            onClick.run();
        }
    }
}
