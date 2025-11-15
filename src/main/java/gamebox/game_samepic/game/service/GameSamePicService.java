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

    public GameSamePicService(PictureRepository repo) {
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
                    .group(hash)
                    .build();
            pictureRepository.save(pic);
        }
    }

    public void newGame(int rows, int cols) {
        int needed = (rows * cols) / 2;
        List<String> pictures = pictureRepository.findAllIds();
        Collections.shuffle(pictures);
        if (pictures.size() < needed) {
            throw new IllegalStateException(ErrorType.NOT_ENOUGH_PICTURES.getMessage());
        }

        gameSamePicBoard = new GameSamePicBoard(rows, cols);
        gameSamePicBoard.initWithPictureIds(pictures.subList(0, needed));
    }

    public Optional<Boolean> flipCard(int index) {
        if (gameSamePicBoard == null) {
            throw new IllegalStateException(ErrorType.GAME_NOT_STARTED.getMessage());
        }
        return gameSamePicBoard.flip(index);
    }

    public GameSamePicBoard getBoard() {
        return gameSamePicBoard;
    }

    public boolean isGameOver() {
        if (gameSamePicBoard == null) {
            return false;
        }
        return gameSamePicBoard.gameOver();
    }

    public int getMoves() {
        if (gameSamePicBoard == null) return 0;
        return gameSamePicBoard.getMoves();
    }

    public Picture getPicture(String pictureId) {
        return pictureRepository.findById(pictureId);
    }

    public void removeGames(String imageGroup) {
        pictureRepository.removeAll(imageGroup);
    }
}


/*

1. 같은 그림찾기 게임시작
현재 리포지토리 상태 { 1,2,3,4,5,6,..32}
2. 뒤로 나감 -> 리포지토리에 그대로 남아있음
다음 게임 시작
{{ 1,2,3,4,5,6,..32}, { 1,2,3,4,5,6,..32}} 똑같은 데이터 다시 들어감

3. 가져올 때 전부 긁어옴 -> 이게 문제의 시작
4. {{ 1,2,3,4,5,6,..32}, { 1,2,3,4,5,6,..32}} 긁어온 데이터 셔플
4.1 {1,2,4,1,2,4,...32} -> 이전 데이터랑 섞여버림
5. 앞에서부터 필요한 갯수 가져옴
6. 중복 데이터 발생
* */