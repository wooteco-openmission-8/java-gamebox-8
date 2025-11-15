package gamebox.game_2048.entity;

import java.util.*;

public class Game2048Board {
    private static final int FILTER = 0;
    private final Tile[][] board;
    private boolean win = false;

    /**
     * @param rows x축 길이
     * @param cols y축 길이
     */
    public Game2048Board(int rows, int cols) {
        this.board = new Tile[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = new Tile(0);
            }
        }
        init();
    }

    /**
     * 보드 무작위 위치에 2개의 타일 생성
     */
    private void init() {
        randomSpawn(2);
    }

    /**
     * @param n 타일을 생성할 갯수
     */
    public void randomSpawn(int n) {
        int spawnCount = 0;

        while (spawnCount < n) {
            int x = (int) (Math.random() * board.length);
            int y = (int) (Math.random() * board[0].length);

            if (Objects.equals(get(x, y).getNumber(), 0)) {
                Tile tile = get(x, y);
                tile.spawn();
                board[x][y] = tile;
                spawnCount++;
            }

            if (spawnCount < n && isFull()) {
                break;
            }
        }
    }

    /**
     * 보드가 꽉 차있는지
     */
    public boolean isFull() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (Objects.equals(get(row, col).getNumber(), 0)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canMove() {
        int rows = board.length;
        int cols = board[0].length;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Tile current = get(r, c);

                // 오른쪽 검사
                if (c + 1 < cols && current.getNumber() == get(r, c + 1).getNumber()) {
                    return true;
                }

                // 아래쪽 검사
                if (r + 1 < rows && current.getNumber() == get(r + 1, c).getNumber()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isWin() {
        return win;
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

    /**
     * 타일을 위로 이동
     */
    public boolean moveUp() {
        boolean changed = false;

        for (int c = 0; c < board[0].length; c++) {
            Tile[] merged = merge(filterColumn(c));
            for (int r = 0; r < board.length; r++) {
                Tile newTile;
                if (merged[r] != null) {
                    newTile = merged[r];
                } else {
                    newTile = Tile.Default();
                }

                if (board[r][c].getNumber() != newTile.getNumber()) {
                    board[r][c] = newTile;
                    changed = true;
                }
            }
        }

        return changed;
    }

    /**
     * 타일을 아래로 이동
     */
    public boolean moveDown() {
        boolean changed = false;

        for (int c = 0; c < board[0].length; c++) {
            List<Tile> tiles = filterColumn(c);
            Collections.reverse(tiles);
            Tile[] merged = merge(tiles);
            Collections.reverse(Arrays.asList(merged));

            for (int r = 0; r < board.length; r++) {
                Tile newTile;
                if (merged[r] != null) {
                    newTile = merged[r];
                } else {
                    newTile = Tile.Default();
                }

                if (board[r][c].getNumber() != newTile.getNumber()) {
                    board[r][c] = newTile;
                    changed = true;
                }
            }
        }

        return changed;
    }

    /**
     * 타일을 왼쪽으로 이동
     */
    public boolean moveLeft() {
        boolean changed = false;

        for (int r = 0; r < board.length; r++) {
            Tile[] merged = merge(filterRows(r));
            for (int c = 0; c < board[0].length; c++) {
                Tile newTile;
                if (merged[c] != null) {
                    newTile = merged[c];
                } else {
                    newTile = Tile.Default();
                }

                if (board[r][c].getNumber() != newTile.getNumber()) {
                    board[r][c] = newTile;
                    changed = true;
                }
            }
        }

        return changed;
    }

    /**
     * 타일을 오른쪽으로 이동
     * - TODO : 중복된 로직 제거
     */
    public boolean moveRight() {
        boolean changed = false;

        for (int r = 0; r < board.length; r++) {
            List<Tile> tiles = filterRows(r);
            Collections.reverse(tiles);
            Tile[] merged = merge(tiles);
            Collections.reverse(Arrays.asList(merged));

            for (int c = 0; c < board[0].length; c++) {
                Tile newTile;
                if (merged[c] != null) {
                    newTile = merged[c];
                } else {
                    newTile = Tile.Default();
                }

                if (board[r][c].getNumber() != newTile.getNumber()) {
                    board[r][c] = newTile;
                    changed = true;
                }
            }
        }

        return changed;
    }



    /**
     * @param columnIndex - y축
     * @return 0이 아닌 열의 Tile만 필터
     */
    private List<Tile> filterColumn(int columnIndex) {
        List<Tile> tiles = new ArrayList<>();
        for (int r = 0; r < board.length; r++) { // 각 행을 순회
            Tile tile = get(r, columnIndex);
            if (tile.getNumber() > FILTER) {
                tiles.add(tile);
            }
        }
        return tiles;
    }

    /**
     * @param rowIndex - x축
     * @return 0이 아닌 행의 Tile만 필터
     */
    private List<Tile> filterRows(int rowIndex) {
        List<Tile> tiles = new ArrayList<>();
        for (int c = 0; c < board[0].length; c++) {
            Tile tile = get(rowIndex, c);
            if (tile.getNumber() > FILTER) {
                tiles.add(tile);
            }
        }

        return tiles;
    }

    /**
     * @param tiles 0이 포함되지 않은 리스트를 인수로 받음
     * @return 병합이 가능한경우 병합한 배열을 반환
     */
    private Tile[] merge(List<Tile> tiles) {
        int n = board.length;
        Tile[] result = new Tile[n];

        int write = 0;
        for (int read = 0; read < tiles.size(); read++) {
            Tile curentTile = tiles.get(read);
            //현재 인덱스 + 1이 전체 리스트 길이보다 작고 다음 값이 현재 값이랑 같으면 병합
            if (read + 1 < tiles.size() && curentTile.getNumber() == tiles.get(read + 1).getNumber()) {
                Tile merged = curentTile.merge(tiles.get(read + 1));
                if (merged.getNumber() == 2048) {
                    win = true;
                }
                result[write++] = merged;
                read++;//{1,2,3,4}
                continue;
            }
            result[write++] = curentTile;
        }

        return result;
    }

    /**
     * 테스트용 함수
     *
     * @param numbers
     */
    public void loadFrom(int[][] numbers) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[0].length; j++) {
                board[i][j] = new Tile(numbers[i][j]); // 0도 실제 타일로 깔아둠(merge 로직 일관성)
            }
        }
    }

    /**
     * 테스트용 함수
     *
     * @return
     */
    public int[][] snapshotNumbers() {
        int[][] out = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                out[i][j] = get(i, j).getNumber();
            }
        }
        return out;
    }
}
