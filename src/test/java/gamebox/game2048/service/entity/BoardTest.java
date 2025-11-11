package gamebox.game2048.service.entity;

import gamebox.game2048.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BoardTest {


    @Test
    @DisplayName("")
    void boardInstanceTest() {
        Board board = new Board(4, 4);
        assertThat(board).isInstanceOf(Board.class);
    }

    @Test
    @DisplayName("기본값 반환 테스트")
    void getBoardTileTest() {
        Board board = new Board(4, 4);
        Tile tile = board.get(0, 0);
        assertThat(tile.getNumber()).isEqualTo(0);
    }

    @Test
    @DisplayName("보드 생성시 최초에 적어도 한개의 타일이 생성된다.")
    void atLeastOneTile() {
        Board board = new Board(4, 4);
        int value = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = board.get(j, i);
                if (tile.getNumber() != 0) {
                    value++;
                }
            }
        }

        assertThat(value).isNotEqualTo(0);
    }

    @Test
    @DisplayName("0이 아닌 값에는 생성 할 수없다")
    void test() {
        Board board = new Board(4, 4);
        board.randomSpawn(100);
        int empty = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = board.get(j, i);
                if (tile.getNumber() == 0) {
                    empty++;
                }
            }
        }
        assertThat(empty).isEqualTo(0);
    }

    @Test
    @DisplayName("보드 생성 시 타일 2개 생성")
    void initTwoTiles() {
        int tileCount = 0;

        Board board = new Board(4, 4);
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                Tile tile = board.get(r, c);
                if (tile.getNumber() != 0) {
                    tileCount++;
                }
            }
        }

        assertThat(tileCount).isEqualTo(2);
    }

    @Test
    @DisplayName("upTile 테스트")
    void testUpTile() {
        Board board = new Board(4, 4);

        int[][] initial = {
                {0, 0, 0, 0},
                {2, 0, 4, 0},
                {2, 0, 0, 0},
                {2, 0, 2, 0}
        };
        board.loadFrom(initial);

        board.upTile();

        int[][] expected = {
                {4, 0, 4, 0},
                {2, 0, 2, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        assertThat(board.snapshotNumbers()).isDeepEqualTo(expected);
    }

    @Test
    @DisplayName("downTile 테스트")
    void testDownTile() {
        Board board = new Board(4, 4);

        int[][] initial = {
                {0, 0, 0, 0},
                {2, 0, 4, 0},
                {2, 0, 0, 0},
                {2, 0, 2, 0}
        };
        board.loadFrom(initial);

        board.downTile();

        int[][] expected = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 4, 0},
                {4, 0, 2, 0}
        };

        assertThat(board.snapshotNumbers()).isDeepEqualTo(expected);
    }
}