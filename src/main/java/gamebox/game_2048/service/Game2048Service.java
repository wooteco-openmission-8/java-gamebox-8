package gamebox.game_2048.service;

import gamebox.game_2048.entity.GameStatus;
import gamebox.game_2048.entity.Tile;
import gamebox.game_2048.entity.Game2048Board;

public class Game2048Service {

    private final Game2048Board game2048Board;

    public Game2048Service(int rows, int cols) {
        this.game2048Board = new Game2048Board(rows, cols);
    }

    public Tile getTile(int row, int col) {
        return game2048Board.get(row, col);
    }

    /**
     * 위로 이동
     *
     * @return
     */
    public boolean moveUp() {
        boolean changed = game2048Board.upTile();
        if(changed) {
            game2048Board.randomSpawn(1);
        }
        return changed;
    }

    /**
     * 아래로 이동
     */
    public boolean moveDown() {
        boolean changed = game2048Board.downTile();
        if(changed) {
            game2048Board.randomSpawn(1);
        }
        return changed;
    }

    /**
     * 왼쪽으로 이동
     */
    public boolean moveLeft() {
        boolean changed = game2048Board.leftTile();
        if(changed) {
            game2048Board.randomSpawn(1);
        }
        return changed;
    }

    /**
     * 오른쪽으로 이동
     */
    public boolean moveRight() {
        boolean changed = game2048Board.rightTile();
        if(changed) {
            game2048Board.randomSpawn(1);
        }
        return changed;
    }


    /**
     * 게임 종료 여부
     */
    public boolean isGameOver() {
        return game2048Board.isFull() && !game2048Board.canMove();
    }

    public boolean isWin() {
        return game2048Board.isWin();
    }

    public GameStatus getGameStatus() {
        if (isWin()) return GameStatus.WIN;
        if (isGameOver()) return GameStatus.GAME_OVER;
        return GameStatus.RUNNING;
    }
}
