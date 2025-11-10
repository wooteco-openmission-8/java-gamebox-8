package gamebox.game2048.service.entity;

import gamebox.game2048.Tile;

import java.util.Objects;

public class Board {
    private static final int CAPACITY = 3;
    private final Tile[][] board;

    /**
     * @param rows x축 길이
     * @param cols y축 길이
     */
    public Board(int rows, int cols) {
        this.board = new Tile[rows][cols];
        init();
    }

    /**
     * 보드 무작위 위치에 타일 생성
     */
    private void init() {
        randomSpawn((int) (Math.random() * CAPACITY) + 1);
    }

    /**
     * @param n
     * 타일을 생성할 갯수
     */
    public void randomSpawn(int n) {
        for (int i = 0; i < n; i++) {
            int x = (int) (Math.random() * board.length);
            int y = (int) (Math.random() * board[0].length);

            if (Objects.equals(get(x, y).getNumber(), 0)) {
                Tile tile = get(x, y);
                tile.spawn(2);
                board[x][y] = tile;
            }
        }
    }

    /**
     * @param x x축 좌표
     * @param y y축 좌표
     * @return x, y의 값<br>
     * 만약 현재 타일이 Null이라면 기본값(Tile(0)) 반환
     */
    public Tile get(int x, int y) {
        Tile tile = board[x][y];
        if (tile == null) {
            return Tile.Default();
        }
        return tile;
    }

    public void upTile() {
        //y축을 아래로 내려가면서 현재 인덱스가 비어있으면 다음 y(n)을 위로 올림
        //모두 밀착 후 투 포인터로 병합 가능한지 확인
    }

    public void downTile() {
    }

    public void leftTile() {
    }

    public void rightTile() {
    }
}
