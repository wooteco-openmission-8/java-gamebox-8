package gamebox.game_samepic.picture.service.entity;

import gamebox.game_samepic.game.entity.GameSamePicBoard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class Game2048GameSamePicBoardTest {

    private GameSamePicBoard gameSamePicBoard;

    @BeforeEach
    void setUp() {
        gameSamePicBoard = new GameSamePicBoard(2, 2); // 2x2 보드 (카드 4장)
        // 그림 id 1,2 각각 2장씩 추가
        // 순서를 맞춰서 0,1이 같은 그림이 되도록
        String id = UUID.randomUUID().toString();
        gameSamePicBoard.initWithPictureIds(List.of(id, id)); // 0,1은 매칭 카드
    }

    @Test
    @DisplayName("첫 번째 카드 뒤집기 후 같은 카드 연속 클릭은 무시")
    void flipSameCardTwice() {
        Optional<Boolean> first = gameSamePicBoard.flip(0);
        assertThat(first).isEmpty();
        assertThat(gameSamePicBoard.getCards().get(0).isFaceUp()).isTrue();

        Optional<Boolean> second = gameSamePicBoard.flip(0);
        assertThat(second).isEmpty(); // 무시됨
        assertThat(gameSamePicBoard.getCards().get(0).isFaceUp()).isTrue();
    }

    @Test
    @DisplayName("두 번째 카드 뒤집기 후 매칭 성공 확인")
    void flipSecondCardMatch() {
        int firstIndex = 0;
        int secondIndex = 1;

        gameSamePicBoard.flip(firstIndex); // 첫 번째 카드
        Optional<Boolean> result = gameSamePicBoard.flip(secondIndex); // 두 번째 카드
        assertThat(result).isPresent();
        assertThat(result.get()).isTrue(); // 매칭 성공

        assertThat(gameSamePicBoard.getCards().get(firstIndex).isMatched()).isTrue();
        assertThat(gameSamePicBoard.getCards().get(secondIndex).isMatched()).isTrue();
    }

    @Test
    @DisplayName("매칭 실패 후 resetUnmatched 호출")
    void flipSecondCardMismatchAndReset() {
        // 실패 시나리오용 새 보드 생성 (0,1 카드 id 다르게)
        gameSamePicBoard = new GameSamePicBoard(2, 2);
        gameSamePicBoard.initWithPictureIds(List.of("1", "2")); // 0,1 카드 id 다르게

        int firstIndex = 0;
        int secondIndex = 1;

        gameSamePicBoard.flip(firstIndex); // 첫 번째 카드
        Optional<Boolean> result = gameSamePicBoard.flip(secondIndex); // 두 번째 카드
        assertThat(result).isPresent();
        assertThat(result.get()).isFalse(); // 매칭 실패

        // 실패 상태에서 카드가 뒤집힌 상태
        assertThat(gameSamePicBoard.getCards().get(firstIndex).isFaceUp()).isTrue();
        assertThat(gameSamePicBoard.getCards().get(secondIndex).isFaceUp()).isTrue();

        // resetUnmatched 호출
        gameSamePicBoard.resetUnmatched();
        assertThat(gameSamePicBoard.getCards().get(firstIndex).isFaceUp()).isFalse();
        assertThat(gameSamePicBoard.getCards().get(secondIndex).isFaceUp()).isFalse();
    }
}
