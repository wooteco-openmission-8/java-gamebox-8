package gamebox.find_same.game.controller;

import gamebox.find_same.game.service.GameService;

public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    public void start(int rows, int cols){
        gameService.newGame(rows, cols);
    }
}
