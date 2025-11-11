package gamebox.find_same.game.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Board {
    private final int rows, cols;
    private final List<Card> cards = new ArrayList<>();
    private Card firstOpen, secondOpen;
    private int moves, matches;
    private boolean waiting = false;

    public Board(int rows, int cols) {
        if ((rows * cols) % 2 != 0) {
            throw new IllegalArgumentException("[Error] 보드판은 짝수형이어야 합니다.");
        }
        this.rows = rows;
        this.cols = cols;
    }

    public void initWithPictureIds(List<Integer> pictureIds) {
        for (int pid : pictureIds) {
            cards.add(new Card(pid));
            cards.add(new Card(pid));
        }
        Collections.shuffle(cards);
        firstOpen = secondOpen = null;
        moves = matches = 0;
    }

    public Optional<Boolean> flip(int index) {
        if (waiting) return Optional.empty();

        Card target = get(index);
        if (target.isMatched()) return Optional.empty();
        if (target == firstOpen) return Optional.empty();

        target.flip();
        if (firstOpen == null) {
            firstOpen = target;
            return Optional.empty();
        }
        secondOpen = target;
        moves++;

        boolean matched = firstOpen.samePicture(secondOpen);
        if (matched) {
            firstOpen.setMatched();
            secondOpen.setMatched();
            matches++;
            firstOpen = null;
            secondOpen = null;
        } else {
            waiting = true;
        }

        return Optional.of(matched);
    }

    private Card get(int index) {
        if (index < 0 || index >= cards.size()) {
            throw new IndexOutOfBoundsException("[Error] 유효하지 않은 카드 인덱스입니다: " + index);
        }
        return cards.get(index);
    }

    public boolean gameOver() {
        return matches * 2 == cards.size();
    }

    public int getMoves() {
        return moves;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void resetUnmatched() {
        if (firstOpen != null && !firstOpen.isMatched()) firstOpen.flip();
        if (secondOpen != null && !secondOpen.isMatched()) secondOpen.flip();
        firstOpen = null;
        secondOpen = null;
        waiting = false;
    }
}