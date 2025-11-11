package gamebox.game2048;

import gamebox.common.Game;

public class Game2048 implements Game {

    private static final String GAME_NAME = "2048";

    /**
     * 게임 시작
     */
    @Override
    public void start() {
        // TODO: Controller 구현 후 추가
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