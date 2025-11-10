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

    public Board(int rows, int cols){
        if ((rows*cols) % 2 != 0){
            throw new IllegalArgumentException("[Error] 보드판은 짝수형이어야 합니다.");
        }
        this.rows = rows;
        this.cols = cols;
    }

    public void initWithPictureIds(List<Integer> pictureIds){
        // 카드 섞기.
        int idCounter = 1;
        for (int pid : pictureIds){
            cards.add(new Card(idCounter++, pid, 0, 0));
            cards.add(new Card(idCounter++, pid, 0, 0));
        }
        Collections.shuffle(cards);

        // 카드로 좌표 채우기.
        int index = 0;
        for (int r=0; r<rows; r++){
            for (int c=0; c<cols; c++){
                Card currentCard = cards.get(index++);
                cards.set(index-1, new Card(currentCard.getId(), currentCard.getPictureId(), r, c));
            }
        }
        firstOpen = secondOpen = null;
        moves = matches = 0;
    }

    public Optional<Boolean> flip(int row, int col){
        Card target = get(row, col);
        if (target == null || target.isMatched()){
            return Optional.empty();
        }
        if (firstOpen != null && secondOpen != null){
            return Optional.empty();
        }

        target.flip();
        if (firstOpen == null){
            firstOpen = target;
            return Optional.empty();
        }
        secondOpen = target;
        moves++;

        boolean matched = firstOpen.samePicture(secondOpen);
        if (matched){
            firstOpen.setMatched();
            secondOpen.setMatched();
            matches++;
        }
        Optional<Boolean> result = Optional.of(matched);
        firstOpen = null;
        secondOpen = null;

        return result;
    }

    private Card get(int row, int col){
        return (Card) cards.stream().filter(card -> card.getRow()==row && card.getCol()==col);
    }

    public boolean gameOver(){
        return matches * 2 == cards.size();
    }

    public int getMoves(){
        return moves;
    }

    public List<Card> getCards(){
        return Collections.unmodifiableList(cards);
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }
}
