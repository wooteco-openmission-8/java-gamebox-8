package gamebox.find_same.game.service;

import gamebox.find_same.game.model.Board;
import gamebox.find_same.picture.service.repository.PictureRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GameService {
    private final PictureRepository pictureRepository;
    private Board board;

    public GameService(PictureRepository repo){
        this.pictureRepository = repo;
    }

    public void newGame(int rows, int cols){
        List<Integer> pictureIds = pictureRepository.findAll().stream()
                .map(picture -> picture.getId()).collect(Collectors.toList());
        if (pictureIds.size()/2 < (rows*cols)/2){
            throw new IllegalStateException("[Error] 그림의 개수가 충분하지 않습니다.");
        }
        board = new Board(rows, cols);
        board.initWithPictureIds(pictureIds);
    }
}
