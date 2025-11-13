package gamebox.find_same.game.controller;

import gamebox.common.Game;
import gamebox.find_same.game.model.Board;
import gamebox.find_same.game.service.GameService;

import gamebox.find_same.picture.service.entity.Picture;
import gamebox.util.Difficulty;
import java.util.Optional;

public class GameController implements Game {
    private final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @Override
    public void start() {
        gameService.initializePictures();
    }

    @Override
    public String getName() {
        return "find-same";
    }

    public void start(Difficulty difficulty) {
        int rows = difficulty.getRows();
        int cols = difficulty.getCols();
        gameService.newGame(rows, cols);
    }

    public Picture getPicture(int pictureId) {
        return gameService.getPicture(pictureId);
    }

    // 카드 뒤집기
    public Optional<Boolean> flip(int index){
        return gameService.flipCard(index);
    }

    // 보드 조회
    public Board getBoard(){
        return gameService.getBoard();
    }

    // 게임 종료 확인
    public boolean isGameOver(){
        return gameService.isGameOver();
    }

    // 이동 횟수 조회
    public int getMoves(){
        return gameService.getMoves();
    }
}