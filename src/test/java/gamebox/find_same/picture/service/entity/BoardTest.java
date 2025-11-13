package gamebox.find_same.picture.service.entity;

import gamebox.find_same.game.model.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board(2, 2); // 2x2 보드 (카드 4장)
        // 그림 id 1,2 각각 2장씩 추가
        // 순서를 맞춰서 0,1이 같은 그림이 되도록
        String id = UUID.randomUUID().toString();
        board.initWithPictureIds(List.of(id, id)); // 0,1은 매칭 카드
    }

    @Test
    @DisplayName("첫 번째 카드 뒤집기 후 같은 카드 연속 클릭은 무시")
    void flipSameCardTwice() {
        Optional<Boolean> first = board.flip(0);
        assertThat(first).isEmpty();
        assertThat(board.getCards().get(0).isFaceUp()).isTrue();

        Optional<Boolean> second = board.flip(0);
        assertThat(second).isEmpty(); // 무시됨
        assertThat(board.getCards().get(0).isFaceUp()).isTrue();
    }

    @Test
    @DisplayName("두 번째 카드 뒤집기 후 매칭 성공 확인")
    void flipSecondCardMatch() {
        int firstIndex = 0;
        int secondIndex = 1;

        board.flip(firstIndex); // 첫 번째 카드
        Optional<Boolean> result = board.flip(secondIndex); // 두 번째 카드
        assertThat(result).isPresent();
        assertThat(result.get()).isTrue(); // 매칭 성공

        assertThat(board.getCards().get(firstIndex).isMatched()).isTrue();
        assertThat(board.getCards().get(secondIndex).isMatched()).isTrue();
    }

    @Test
    @DisplayName("매칭 실패 후 resetUnmatched 호출")
    void flipSecondCardMismatchAndReset() {
        // 실패 시나리오용 새 보드 생성 (0,1 카드 id 다르게)
        board = new Board(2, 2);
        board.initWithPictureIds(List.of("1", "2")); // 0,1 카드 id 다르게

        int firstIndex = 0;
        int secondIndex = 1;

        board.flip(firstIndex); // 첫 번째 카드
        Optional<Boolean> result = board.flip(secondIndex); // 두 번째 카드
        assertThat(result).isPresent();
        assertThat(result.get()).isFalse(); // 매칭 실패

        // 실패 상태에서 카드가 뒤집힌 상태
        assertThat(board.getCards().get(firstIndex).isFaceUp()).isTrue();
        assertThat(board.getCards().get(secondIndex).isFaceUp()).isTrue();

        // resetUnmatched 호출
        board.resetUnmatched();
        assertThat(board.getCards().get(firstIndex).isFaceUp()).isFalse();
        assertThat(board.getCards().get(secondIndex).isFaceUp()).isFalse();
    }
}
