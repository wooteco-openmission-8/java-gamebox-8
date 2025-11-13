package gamebox.view.gameScreen.game2048;

import gamebox.game2048.Tile;
import gamebox.game2048.service.entity.Board;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Game2048Panel extends JPanel {
    private static final int GRID_GAP = 10;
    private static final int GRID_SIZE = 4;

    private final JPanel resetPanel = new JPanel();
    private JPanel gamePanel = new JPanel();

    private TilePanel[][] tile;
    private Board board;

    /**
     * Game2048Panel = resetPanel + gamePanel
     *
     * resetPanel: 리셋 버튼
     * gamePanel: 2048 게임
     */
    public Game2048Panel() {
        setLayout(new BorderLayout());
        setFocusable(false);

        setResetPanel();
        setGamePanel();

        add(resetPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
    }

    private void initBoard() {
        // TODO: Board(Tile[][]) 초기화
        board = new Board(GRID_SIZE, GRID_SIZE);
        drawTiles();
    }

    public void updateBoard() {
        // TODO: 전달받은 Board 상태를 패널 내부 변수에 저장
        gamePanel.removeAll();
        drawTiles();
        revalidate();
        repaint();
    }

    private void drawTiles() {
        tile = new TilePanel[GRID_SIZE][GRID_SIZE];
        for (int r=0; r<GRID_SIZE; r++) {
            for (int c=0; c<GRID_SIZE; c++) {
                TilePanel currentTile = tile[r][c];
                currentTile = new TilePanel();
                currentTile.setFocusable(false);

                /**
                 * 2048 컨트롤러 구현 완료 후 수정
                 */
                Tile tile = board.get(r, c);
                if (tile.getNumber() != 0) {
                    currentTile.setTile(tile.getNumber());
                    currentTile.setColor(tile.getBackgroundColor());
                }

                gamePanel.add(currentTile);
            }
        }
    }

    private void setResetPanel() {
        resetPanel.setBackground(Color.white);
        JButton resetButton = new JButton("reset");
        resetButton.setFocusable(false);
        resetButton.addActionListener(e -> {
            int reset = JOptionPane.showConfirmDialog(
                    this,
                    "게임을 초기화하시겠습니까?",
                    "확인",
                    JOptionPane.YES_NO_OPTION
            );
            if (reset == JOptionPane.YES_OPTION) {
                gamePanel.removeAll();
                initBoard();
                revalidate();
                repaint();
                SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
            }
        });
        resetPanel.add(resetButton);
    }

    private void setGamePanel() {
        gamePanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE, GRID_GAP, GRID_GAP));
        gamePanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        gamePanel.setBackground(Color.white);

        initBoard();

        addKeyListener();

        // 키 입력이 가능하도록 설정.
        gamePanel.setFocusable(true);
        gamePanel.setFocusTraversalKeysEnabled(false);

        // 화면이 완전히 보인 뒤 실행하도록.
        SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
    }

    private void addKeyListener() {
        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    System.out.println("위 방향키 누름");
                    board.upTile();
                    board.randomSpawn(1);
                    updateBoard();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    System.out.println("아래 방향키 누름");
                    board.downTile();
                    board.randomSpawn(1);
                    updateBoard();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    System.out.println("왼쪽 방향키 누름");
                    board.leftTile();
                    board.randomSpawn(1);
                    updateBoard();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    System.out.println("오른쪽 방향키 누름");
                    board.rightTile();
                    board.randomSpawn(1);
                    updateBoard();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO: 내부 Board 상태를 읽어 타일을 화면에 그리기
    }
}
