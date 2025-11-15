package gamebox.game_samepic.game.entity;

public enum Difficulty {
    EASY(4, 4),
    MEDIUM(6, 6),
    HARD(8, 8);

    private final int rows;
    private final int cols;

    Difficulty(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}