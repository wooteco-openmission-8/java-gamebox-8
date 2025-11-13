package gamebox.game2048.service;

import gamebox.game2048.GameStatus;
import gamebox.game2048.Tile;
import gamebox.game2048.service.entity.Board;

public class GameService {

    private final Board board;

    public GameService(int rows, int cols) {
        this.board = new Board(rows, cols);
    }

    public Tile getTile(int row, int col) {
        return board.get(row, col);
    }

    /**
     * 위로 이동
     *
     * @return
     */
    public boolean moveUp() {
        boolean changed = board.upTile();
        if(changed) {
            board.randomSpawn(1);
        }
        return changed;
    }

    /**
     * 아래로 이동
     */
    public boolean moveDown() {
        boolean changed = board.downTile();
        if(changed) {
            board.randomSpawn(1);
        }
        return changed;
    }

    /**
     * 왼쪽으로 이동
     */
    public boolean moveLeft() {
        boolean changed = board.leftTile();
        if(changed) {
            board.randomSpawn(1);
        }
        return changed;
    }

    /**
     * 오른쪽으로 이동
     */
    public boolean moveRight() {
        boolean changed = board.rightTile();
        if(changed) {
            board.randomSpawn(1);
        }
        return changed;
    }


    /**
     * 게임 종료 여부
     */
    public boolean isGameOver() {
        return board.isFull() && !board.canMove();
    }

    public boolean isWin() {
        return board.isWin();
    }

    public GameStatus getGameStatus() {
        if (isWin()) return GameStatus.WIN;
        if (isGameOver()) return GameStatus.GAME_OVER;
        return GameStatus.RUNNING;
    }
}
