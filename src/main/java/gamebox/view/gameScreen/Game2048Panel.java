package gamebox.view.gameScreen;

import javax.swing.*;
import java.awt.*;

public class Game2048Panel extends JPanel {
    private static final int GRID_SIZE = 4;

    public Game2048Panel() {
        setupPanel();
    }

    private void setupPanel() {
        setOpaque(true);
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE, 5, 5)); // 5픽셀 간격
        setBackground(Color.lightGray);
    }

    private void initBoard() {
        // TODO: Board(Tile[][]) 초기화
    }

    public void updateBoard() {
        // TODO: 전달받은 Board 상태를 패널 내부 변수에 저장
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO: 내부 Board 상태를 읽어 타일을 화면에 그리기
    }
}
