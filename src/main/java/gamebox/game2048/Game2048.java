package gamebox.game2048;

import gamebox.common.Game;
import gamebox.game2048.service.GameService;

public class Game2048 implements Game {

    private static final String GAME_NAME = "2048";
    private GameService gameService;

    @Override
    public void start() {
        gameService = new GameService(4, 4);
    }

    // 이동 메서드
    public boolean moveUp() {
        return gameService.moveUp();
    }

    public boolean moveDown() {
        return gameService.moveDown();
    }

    public boolean moveLeft() {
        return gameService.moveLeft();
    }

    public boolean moveRight() {
        return gameService.moveRight();
    }

    public Tile getTile(int row, int col) {
        return gameService.getTile(row, col);
    }

    public GameStatus getGameStatus() {
        return gameService.getGameStatus();
    }

    /**
     * 게임 이름 반환
     * @return "2048"
     */
    @Override
    public String getName() {
        return GAME_NAME;
    }

}