package gamebox.find_same.game.service;

import gamebox.find_same.game.model.Board;
import gamebox.find_same.picture.service.entity.Picture;
import gamebox.find_same.picture.service.repository.PictureRepository;
import gamebox.util.HashMaker;
import gamebox.util.exceptions.ErrorType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GameService {
    private final PictureRepository pictureRepository;
    private Board board;

    public GameService(PictureRepository repo){
        this.pictureRepository = repo;
    }

    public void initializePictures() {
        String hash = UUID.randomUUID().toString();
        for (int i = 1; i <= 32; i++) {
            Picture pic = new Picture.Builder()
                    .id(HashMaker.make(i, hash))
                    .title("Picture " + i)
                    .path("images/find_same/pic" + i + ".png")
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

        board = new Board(rows, cols);
        board.initWithPictureIds(pictures.subList(0, needed));
    }

    public Optional<Boolean> flipCard(int index){
        if (board == null){
            throw new IllegalStateException(ErrorType.GAME_NOT_STARTED.getMessage());
        }
        return board.flip(index);
    }

    public Board getBoard(){
        return board;
    }

    public boolean isGameOver(){
        if (board == null) return false;
        return board.gameOver();
    }

    public int getMoves(){
        if (board == null) return 0;
        return board.getMoves();
    }

    public Picture getPicture(String pictureId) {
        return pictureRepository.findById(pictureId);
    }

}