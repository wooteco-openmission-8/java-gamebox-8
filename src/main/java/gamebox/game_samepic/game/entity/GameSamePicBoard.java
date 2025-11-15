package gamebox.game_samepic.game.entity;

import gamebox.util.exceptions.ErrorType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GameSamePicBoard {
    private final int rows, cols;
    private final List<Card> cards = new ArrayList<>();
    private Card firstOpen, secondOpen;
    private int moves, matches;
    private boolean waiting = false;

    public GameSamePicBoard(int rows, int cols) {
        if ((rows * cols) % 2 != 0) {
            throw new IllegalArgumentException(ErrorType.INVALID_BOARD_SIZE.getMessage());
        }
        this.rows = rows;
        this.cols = cols;
    }

    public void initWithPictureIds(List<String> pictureIds) {
        for (String pid : pictureIds) {
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
        if (target == null) {
            return Optional.empty();
        }

        boolean setOpenedCards = selectCards(target);
        if (!setOpenedCards) {
            return Optional.empty();
        }

        boolean matched = setMatchedCards();
        return Optional.of(matched);
    }

    private boolean selectCards(Card target) {
        if (target.isMatched() || target == firstOpen) {
            return false;
        }

        target.flip();
        if (firstOpen == null) {
            firstOpen = target;
            return false;
        }
        secondOpen = target;
        moves++;

        return true;
    }

    private boolean setMatchedCards() {
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

        return matched;
    }

    private Card get(int index) {
        if (index < 0 || index >= cards.size()) {
            throw new IndexOutOfBoundsException(ErrorType.INVALID_INDEX.getMessage() + index);
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

    public Card getCard(int index) {
        return cards.get(index);
    }
}