package gamebox.game_samepic.game.entity;

import java.util.Objects;

public class Card {
    private final String pictureId;
    private boolean faceUp;
    private boolean matched;

    public Card(String pictureId) {
        this.pictureId = pictureId;
    }

    public void flip() {
        if (!matched) {
            faceUp = !faceUp;
        }
    }

    public boolean samePicture(Card other){ return Objects.equals(this.pictureId, other.pictureId); }

    public void setMatched(){ matched = true; }

    public String getPictureId(){ return pictureId; }

    public boolean isFaceUp(){ return faceUp; }

    public boolean isMatched(){ return matched; }
}