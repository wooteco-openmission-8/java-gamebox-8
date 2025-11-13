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
    @DisplayName("Tile 이동 및 병합 테스트")
    void tileMoveTest() {
        Board board = new Board(4, 4);

        // 초기 상태
        int[][] initial = {
                {2, 2, 2, 4},
                {2, 2, 4, 2},
                {2, 4, 2, 2},
                {4, 2, 2, 2}
        };

        // ------------------- upTile 테스트 -------------------
        board.loadFrom(initial);
        board.upTile();
        int[][] upExpected = {
                {4, 4, 2, 4},
                {2, 4, 4, 4},
                {4, 2, 4, 2},
                {0, 0, 0, 0}
        };
        assertThat(board.snapshotNumbers()).isDeepEqualTo(upExpected);

        // ------------------- downTile 테스트 -------------------
        board.loadFrom(initial);
        board.downTile();
        int[][] downExpected = {
                {0, 0, 0, 0},
                {2, 4, 2, 4},
                {4, 4, 4, 2},
                {4, 2, 4, 4}
        };
        assertThat(board.snapshotNumbers()).isDeepEqualTo(downExpected);

        // ------------------- leftTile 테스트 -------------------
        board.loadFrom(initial);
        board.leftTile();
        int[][] leftExpected = {
                {4, 2, 4, 0},
                {4, 4, 2, 0},
                {2, 4, 4, 0},
                {4, 4, 2, 0}
        };
        assertThat(board.snapshotNumbers()).isDeepEqualTo(leftExpected);

        // ------------------- rightTile 테스트 -------------------
        board.loadFrom(initial);
        board.rightTile();
        int[][] rightExpected = {
                {0, 2, 4, 4},
                {0, 4, 4, 2},
                {0, 2, 4, 4},
                {0, 4, 2, 4}
        };
        assertThat(board.snapshotNumbers()).isDeepEqualTo(rightExpected);
    }

    @Test
    @DisplayName("upTile 이동 가능 테스트")
    void upTileMovable() {
        Board board = new Board(4, 4);
        board.loadFrom(new int[][]{
                {2, 0, 2, 4},
                {2, 4, 0, 4},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });

        boolean changed = board.upTile();
        assertThat(changed).isTrue();
    }

    @Test
    @DisplayName("upTile 이동 불가 테스트")
    void upTileImmovable() {
        Board board = new Board(4, 4);
        board.loadFrom(new int[][]{
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        });

        boolean changed = board.upTile();
        assertThat(changed).isFalse();
    }

    @Test
    @DisplayName("downTile 이동 가능 테스트")
    void downTileMovable() {
        Board board = new Board(4, 4);
        board.loadFrom(new int[][]{
                {2, 0, 2, 4},
                {2, 4, 0, 4},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });

        boolean changed = board.downTile();
        assertThat(changed).isTrue();
    }

    @Test
    @DisplayName("downTile 이동 불가 테스트")
    void downTileImmovable() {
        Board board = new Board(4, 4);
        board.loadFrom(new int[][]{
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        });

        boolean changed = board.downTile();
        assertThat(changed).isFalse();
    }

    @Test
    @DisplayName("leftTile 이동 가능 테스트")
    void leftTileMovable() {
        Board board = new Board(4, 4);
        board.loadFrom(new int[][]{
                {0, 2, 2, 4},
                {2, 0, 4, 4},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });

        boolean changed = board.leftTile();
        assertThat(changed).isTrue();
    }

    @Test
    @DisplayName("leftTile 이동 불가 테스트")
    void leftTileImmovable() {
        Board board = new Board(4, 4);
        board.loadFrom(new int[][]{
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        });

        boolean changed = board.leftTile();
        assertThat(changed).isFalse();
    }

    @Test
    @DisplayName("rightTile 이동 가능 테스트")
    void rightTileMovable() {
        Board board = new Board(4, 4);
        board.loadFrom(new int[][]{
                {0, 2, 2, 4},
                {2, 0, 4, 4},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });

        boolean changed = board.rightTile();
        assertThat(changed).isTrue();
    }

    @Test
    @DisplayName("rightTile 이동 불가 테스트")
    void rightTileImmovable() {
        Board board = new Board(4, 4);
        board.loadFrom(new int[][]{
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        });

        boolean changed = board.rightTile();
        assertThat(changed).isFalse();
    }
}