package gamebox.game_samepic.game.service;

import gamebox.game_samepic.game.entity.GameSamePicBoard;
import gamebox.game_samepic.picture.service.entity.Picture;
import gamebox.game_samepic.picture.service.repository.PictureRepository;
import gamebox.util.HashMaker;
import gamebox.util.exceptions.ErrorType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GameSamePicService {
    private final PictureRepository pictureRepository;
    private GameSamePicBoard gameSamePicBoard;

    public GameSamePicService(PictureRepository repo){
        this.pictureRepository = repo;
    }

    public void initializePictures() {
        String hash = UUID.randomUUID().toString();
        for (int i = 1; i <= 32; i++) {
            Picture pic = new Picture.Builder()
                    .id(HashMaker.make(i, hash))
                    .title("Picture " + i)
                    .path("/images/find_same/pic" + i + ".png")
                    .visible(false)
                    .checkCount(0)
                    .build();
            pictureRepository.save(pic);
        }
    }

    public void newGame(int rows, int cols){
        int needed = (rows * cols) / 2;
        List<String> pictures = pictureRepository.findAllIds();

        if (pictures.size() < needed){
            throw new IllegalStateException(ErrorType.NOT_ENOUGH_PICTURES.getMessage());
        }

        Collections.shuffle(pictures);

        gameSamePicBoard = new GameSamePicBoard(rows, cols);
        gameSamePicBoard.initWithPictureIds(pictures.subList(0, needed));
    }

    public Optional<Boolean> flipCard(int index){
        if (gameSamePicBoard == null){
            throw new IllegalStateException(ErrorType.GAME_NOT_STARTED.getMessage());
        }
        return gameSamePicBoard.flip(index);
    }

    public GameSamePicBoard getBoard(){
        return gameSamePicBoard;
    }

    public boolean isGameOver(){
        if (gameSamePicBoard == null) return false;
        return gameSamePicBoard.gameOver();
    }

    public int getMoves(){
        if (gameSamePicBoard == null) return 0;
        return gameSamePicBoard.getMoves();
    }

    public Picture getPicture(String pictureId) {
        return pictureRepository.findById(pictureId);
    }

}