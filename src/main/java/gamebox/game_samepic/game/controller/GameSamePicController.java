package gamebox.game_samepic.game.controller;

import gamebox.common.Game;
import gamebox.game_samepic.game.entity.GameSamePicBoard;
import gamebox.game_samepic.game.service.GameSamePicService;

import gamebox.game_samepic.picture.service.entity.Picture;
import gamebox.game_samepic.game.entity.Difficulty;
import java.util.Optional;

public class GameSamePicController implements Game {
    private final GameSamePicService gameSamePicService;

    public GameSamePicController(GameSamePicService gameSamePicService){
        this.gameSamePicService = gameSamePicService;
    }

    @Override
    public void start() {
        gameSamePicService.initializePictures();
    }

    @Override
    public String getName() {
        return "find-same";
    }

    public void start(Difficulty difficulty) {
        int rows = difficulty.getRows();
        int cols = difficulty.getCols();
        gameSamePicService.newGame(rows, cols);
    }

    public Picture getPicture(String pictureId) {
        return gameSamePicService.getPicture(pictureId);
    }

    // 카드 뒤집기
    public Optional<Boolean> flip(int index){
        return gameSamePicService.flipCard(index);
    }

    // 보드 조회
    public GameSamePicBoard getBoard(){
        return gameSamePicService.getBoard();
    }

    // 게임 종료 확인
    public boolean isGameOver(){
        return gameSamePicService.isGameOver();
    }

    // 이동 횟수 조회
    public int getMoves(){
        return gameSamePicService.getMoves();
    }
}