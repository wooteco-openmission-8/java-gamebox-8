package gamebox.game_2048.service.entity;

import gamebox.game_2048.entity.Tile;
import gamebox.game_2048.entity.Game2048Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class Game2048GameSamePicBoardTest {


    @Test
    @DisplayName("")
    void boardInstanceTest() {
        Game2048Board game2048Board = new Game2048Board(4, 4);
        assertThat(game2048Board).isInstanceOf(Game2048Board.class);
    }
    @Test
    @DisplayName("보드 생성시 최초에 적어도 한개의 타일이 생성된다.")
    void atLeastOneTile() {
        Game2048Board game2048Board = new Game2048Board(4, 4);
        int value = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = game2048Board.get(j, i);
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
        Game2048Board game2048Board = new Game2048Board(4, 4);
        game2048Board.randomSpawn(100);
        int empty = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = game2048Board.get(i, j);
                if (tile.getNumber() == 0) {
                    empty++;
                }
            }
        }
        assertThat(empty).isEqualTo(0);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = game2048Board.get(i, j);
                System.out.print(tile.getNumber() + " ");
            }
            System.out.println();
        }

        game2048Board.moveUp();

        System.out.println("BoardTest.test");

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = game2048Board.get(i, j);
                System.out.print(tile.getNumber() + " ");
            }
            System.out.println();
        }

    }

    @Test
    @DisplayName("보드 생성 시 타일 2개 생성")
    void initTwoTiles() {
        int tileCount = 0;

        Game2048Board game2048Board = new Game2048Board(4, 4);
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                Tile tile = game2048Board.get(r, c);
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
        Game2048Board game2048Board = new Game2048Board(4, 4);

        // 초기 상태
        int[][] initial = {
                {2, 2, 2, 4},
                {2, 2, 4, 2},
                {2, 4, 2, 2},
                {4, 2, 2, 2}
        };

        // ------------------- upTile 테스트 -------------------
        game2048Board.loadFrom(initial);
        game2048Board.moveUp();
        int[][] upExpected = {
                {4, 4, 2, 4},
                {2, 4, 4, 4},
                {4, 2, 4, 2},
                {0, 0, 0, 0}
        };
        assertThat(game2048Board.snapshotNumbers()).isDeepEqualTo(upExpected);

        // ------------------- downTile 테스트 -------------------
        game2048Board.loadFrom(initial);
        game2048Board.moveDown();
        int[][] downExpected = {
                {0, 0, 0, 0},
                {2, 4, 2, 4},
                {4, 4, 4, 2},
                {4, 2, 4, 4}
        };
        assertThat(game2048Board.snapshotNumbers()).isDeepEqualTo(downExpected);

        // ------------------- leftTile 테스트 -------------------
        game2048Board.loadFrom(initial);
        game2048Board.moveLeft();
        int[][] leftExpected = {
                {4, 2, 4, 0},
                {4, 4, 2, 0},
                {2, 4, 4, 0},
                {4, 4, 2, 0}
        };
        assertThat(game2048Board.snapshotNumbers()).isDeepEqualTo(leftExpected);

        // ------------------- rightTile 테스트 -------------------
        game2048Board.loadFrom(initial);
        game2048Board.moveRight();
        int[][] rightExpected = {
                {0, 2, 4, 4},
                {0, 4, 4, 2},
                {0, 2, 4, 4},
                {0, 4, 2, 4}
        };
        assertThat(game2048Board.snapshotNumbers()).isDeepEqualTo(rightExpected);
    }

    @Test
    @DisplayName("upTile 이동 가능 테스트")
    void upTileMovable() {
        Game2048Board game2048Board = new Game2048Board(4, 4);
        game2048Board.loadFrom(new int[][]{
                {2, 0, 2, 4},
                {2, 4, 0, 4},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });

        boolean changed = game2048Board.moveUp();
        assertThat(changed).isTrue();
    }

    @Test
    @DisplayName("upTile 이동 불가 테스트")
    void upTileImmovable() {
        Game2048Board game2048Board = new Game2048Board(4, 4);
        game2048Board.loadFrom(new int[][]{
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        });

        boolean changed = game2048Board.moveUp();
        assertThat(changed).isFalse();
    }

    @Test
    @DisplayName("downTile 이동 가능 테스트")
    void downTileMovable() {
        Game2048Board game2048Board = new Game2048Board(4, 4);
        game2048Board.loadFrom(new int[][]{
                {2, 0, 2, 4},
                {2, 4, 0, 4},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });

        boolean changed = game2048Board.moveDown();
        assertThat(changed).isTrue();
    }

    @Test
    @DisplayName("downTile 이동 불가 테스트")
    void downTileImmovable() {
        Game2048Board game2048Board = new Game2048Board(4, 4);
        game2048Board.loadFrom(new int[][]{
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        });

        boolean changed = game2048Board.moveDown();
        assertThat(changed).isFalse();
    }

    @Test
    @DisplayName("leftTile 이동 가능 테스트")
    void leftTileMovable() {
        Game2048Board game2048Board = new Game2048Board(4, 4);
        game2048Board.loadFrom(new int[][]{
                {0, 2, 2, 4},
                {2, 0, 4, 4},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });

        boolean changed = game2048Board.moveLeft();
        assertThat(changed).isTrue();
    }

    @Test
    @DisplayName("leftTile 이동 불가 테스트")
    void leftTileImmovable() {
        Game2048Board game2048Board = new Game2048Board(4, 4);
        game2048Board.loadFrom(new int[][]{
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        });

        boolean changed = game2048Board.moveLeft();
        assertThat(changed).isFalse();
    }

    @Test
    @DisplayName("rightTile 이동 가능 테스트")
    void rightTileMovable() {
        Game2048Board game2048Board = new Game2048Board(4, 4);
        game2048Board.loadFrom(new int[][]{
                {0, 2, 2, 4},
                {2, 0, 4, 4},
                {0, 2, 2, 0},
                {0, 0, 0, 0}
        });

        boolean changed = game2048Board.moveRight();
        assertThat(changed).isTrue();
    }

    @Test
    @DisplayName("rightTile 이동 불가 테스트")
    void rightTileImmovable() {
        Game2048Board game2048Board = new Game2048Board(4, 4);
        game2048Board.loadFrom(new int[][]{
                {2, 4, 2, 4},
                {4, 2, 4, 2},
                {2, 4, 2, 4},
                {4, 2, 4, 2}
        });

        boolean changed = game2048Board.moveRight();
        assertThat(changed).isFalse();
    }
}