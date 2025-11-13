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
//랜덤값 제어 불가로 주석처리
//    @Test
//    @DisplayName("기본값 반환 테스트")
//    void getBoardTileTest() {
//        Board board = new Board(4, 4);
//        Tile tile = board.get(0, 0);
//        assertThat(tile.getNumber()).isEqualTo(0);
//    }
//
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
                Tile tile = board.get(i, j);
                if (tile.getNumber() == 0) {
                    empty++;
                }
            }
        }
        assertThat(empty).isEqualTo(0);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = board.get(i, j);
                System.out.print(tile.getNumber() + " ");
            }
            System.out.println();
        }

        board.upTile();

        System.out.println("BoardTest.test");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = board.get(i, j);
                System.out.print(tile.getNumber() + " ");
            }
            System.out.println();
        }

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
    @DisplayName("upTile 테스트")
    void testUpTile2() {
        Board board = new Board(4, 4);

        int[][] initial = {
                {2, 2, 2, 2},
                {2, 2, 2, 4},
                {2, 2, 2, 2},
                {4, 2, 2, 2},
        };
        board.loadFrom(initial);

        board.upTile();

        int[][] expected = {
                {4, 4, 4, 2},
                {2, 2, 2, 4},
                {4, 2, 2, 2},
                {0, 0, 0, 2}
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

    @Test
    @DisplayName("leftTile테스트")
    void leftTile() {
        Board board = new Board(4, 4);

        int[][] initial = {
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2},
                {2, 2, 2, 2}
        };
        board.loadFrom(initial);

        board.leftTile();

        int[][] expected = {
                {4, 4, 0, 0},
                {4, 4, 0, 0},
                {4, 4, 0, 0},
                {4, 4, 0, 0},
        };

        assertThat(board.snapshotNumbers()).isDeepEqualTo(expected);

        board.leftTile();

        int[][] expected2 = {
                {8, 0, 0, 0},
                {8, 0, 0, 0},
                {8, 0, 0, 0},
                {8, 0, 0, 0},
        };
        assertThat(board.snapshotNumbers()).isDeepEqualTo(expected2);
    }

    @Test
    @DisplayName("rightTile테스트")
    void rightTile() {
        Board board = new Board(4, 4);
        int[][] initial = {
                {4, 4, 0, 0},
                {4, 4, 0, 0},
                {4, 4, 0, 0},
                {4, 4, 0, 0},
        };
        board.loadFrom(initial);

        board.rightTile();

        int[][] expected = {
                {0, 0, 0, 8},
                {0, 0, 0, 8},
                {0, 0, 0, 8},
                {0, 0, 0, 8},
        };
        assertThat(board.snapshotNumbers()).isDeepEqualTo(expected);


    }
    @Test
    @DisplayName("right 일부만 병합 가능한 경우 테스트")
    void rightTile2() {
        Board board = new Board(4, 4);
        int[][] initial = {
                {4, 2, 8, 2},
                {4, 2, 8, 2},
                {4, 4, 8, 2},
                {4, 2, 8, 2},
        };
        board.loadFrom(initial);

        board.rightTile();

        int[][] expected = {
                {4, 2, 8, 2},
                {4, 2, 8, 2},
                {0, 8, 8, 2},
                {4, 2, 8, 2},
        };
        assertThat(board.snapshotNumbers()).isDeepEqualTo(expected);


    }
}