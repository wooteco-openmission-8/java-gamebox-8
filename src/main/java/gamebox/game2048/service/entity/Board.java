package gamebox.game2048.service.entity;

import gamebox.game2048.Tile;

import java.util.*;

public class Board {
    private static final int FILTER = 0;
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
    private boolean isFull() {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (Objects.equals(get(row, col).getNumber(), 0)) {
                    return false;
                }
            }
        }
        return true;
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
     * 타일을 위로 올렸을 떄.
     * 숫자를 먼저 머지한 후,
     * 타일을 올릴 수 있을 때까지 반복.
     */
    public void upTile() {
        mergeNumbersWhenUpTile();
        while (moveTilesWhenUpTile()) {

        }
    }

    public void downTile() {
        mergeNumbersWhenDownTile();
        while (moveTilesWhenDownTile()) {

        }
    }

    /**
     * 위로 올렸을 때, 합칠 수 있는 숫자를 합침
     */
    private void mergeNumbersWhenUpTile() {
        for (int c = 0; c < board.length; c++) {
            if (hasAtLeastTwoTilesInColumn(c)) {
                List<Tile> mergableTiles = getMergableTilesWhenUpTile(c);
                Tile aboveTile = mergableTiles.get(0);
                Tile bottomTile = mergableTiles.get(1);

                if (hasSameNumber(aboveTile, bottomTile)) {
                    aboveTile.mergeWith(bottomTile);
                    bottomTile.delete();
                }
            }
        }
    }

    /**
     * 타일을 위로 올리는 함수
     *
     * @return 이동한 타일이 있는지
     */
    private boolean moveTilesWhenUpTile() {
        boolean movedAny = false;

        for (int r = 1; r < board.length; r++) {
            for (int c = 0; c < board.length; c++) {
                Tile targetTile = get(r - 1, c);
                Tile currentTile = get(r, c);
                if (currentTile.moveTo(targetTile)) {
                    movedAny = true;
                }
            }
        }

        return movedAny;
    }

    /**
     * @param c 열 번호
     *          합쳐질 수 있는 위에 타일과 아래 타일을 설정.
     */
    private List<Tile> getMergableTilesWhenUpTile(int c) {
        List<Tile> mergableTiles = new ArrayList<>();
        for (int r = 0; r < board.length; r++) {
            Tile currentTile = get(r, c);
            if (currentTile.getNumber() != 0) {
                mergableTiles.add(currentTile);
            }

            if (mergableTiles.size() == 2) {
                break;
            }
        }

        return mergableTiles;
    }

    private void mergeNumbersWhenDownTile() {
        for (int c = 0; c < board.length; c++) {
            if (hasAtLeastTwoTilesInColumn(c)) {
                List<Tile> mergableTiles = getMergableTilesWhenDownTile(c);
                Tile bottomTile = mergableTiles.get(0);
                Tile aboveTile = mergableTiles.get(1);

                if (hasSameNumber(bottomTile, aboveTile)) {
                    bottomTile.mergeWith(aboveTile);
                    aboveTile.delete();
                }
            }
        }
    }

    /**
     * @param c 열 번호
     *          합쳐질 수 있는 위에 타일과 아래 타일을 설정.
     */
    private List<Tile> getMergableTilesWhenDownTile(int c) {
        List<Tile> mergableTiles = new ArrayList<>();
        for (int r = board.length - 1; r >= 0; r--) {
            Tile currentTile = get(r, c);
            if (currentTile.getNumber() != 0) {
                mergableTiles.add(currentTile);
            }

            if (mergableTiles.size() == 2) {
                break;
            }
        }

        return mergableTiles;
    }

    /**
     * 타일을 위로 올리는 함수
     *
     * @return 이동한 타일이 있는지
     */
    private boolean moveTilesWhenDownTile() {
        boolean movedAny = false;

        for (int r = board.length - 2; r >= 0; r--) {
            for (int c = 0; c < board.length; c++) {
                Tile targetTile = get(r + 1, c);
                Tile currentTile = get(r, c);
                if (currentTile.moveTo(targetTile)) {
                    movedAny = true;
                }
            }
        }

        return movedAny;
    }


    /**
     * @param c 열 번호
     * @return 0이 아닌 타일이 2개 이상
     * 한 열에서 0이 아닌 타일이 2개 이상이어야 합치든 말든 할 수 있는 기본 조건이 됨.
     */
    private boolean hasAtLeastTwoTilesInColumn(int c) {
        int tileCount = 0;
        for (int r = 0; r < board.length; r++) {
            Tile tile = get(r, c);
            if (tile.getNumber() != 0) {
                tileCount++;
            }
        }

        return tileCount >= 2;
    }

    /**
     * @param aboveTile  위에 있는 타일
     * @param bottomTile 아래 있는 타일
     * @return 두 타일의 번호가 같음
     */
    private boolean hasSameNumber(Tile aboveTile, Tile bottomTile) {
        return aboveTile.getNumber() == bottomTile.getNumber();
    }


    /**
     * 타일을 왼쪽으로 이동 시켰을때 동작<br>
     * 수행 후 무작위 위치에 타일 생성 필요
     */
    public void leftTile() {
        for (int i = FILTER; i < board.length; i++) {
            board[i] = merge(filterTiles(i));
        }
    }

    /**
     * @param index
     * @return 0이 아닌 Tile만 필터
     */
    private List<Tile> filterTiles(int index) {
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            Tile tile = get(index, i);
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
                result[write++] = curentTile.merge(tiles.get(read + 1));
                read++;//{1,2,3,4}
                continue;
            }
            result[write++] = curentTile;
        }

        return result;
    }

    /**
     * 타일을 오른쪽으로 이동 시켰을 때 동작<br>
     * 수행 후 무작위 위치에 타일 생성 필요
     */
    public void rightTile() {
        for (int i = 0; i < board.length; i++) {
            List<Tile> tiles = filterTiles(i);
            Collections.reverse(tiles);// i번째 행 타일 뒤집기 {2,2,0,2} -> {2,0,2,2}
            Tile[] merge = merge(tiles);//병합
            Collections.reverse(Arrays.asList(merge)); // 다시 뒤집기  {4,2,0,0} -> {0,0,2,4}
            board[i] = merge;
        }
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
