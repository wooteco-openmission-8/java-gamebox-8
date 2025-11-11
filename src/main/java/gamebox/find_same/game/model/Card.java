package gamebox.find_same.game.model;

public class Card {
    private final int pictureId;
    private boolean faceUp;
    private boolean matched;

    public Card(int pictureId) {
        this.pictureId = pictureId;
    }

    public void flip() {
        if (!matched) {
            faceUp = !faceUp;
        }
    }

    public boolean samePicture(Card other){ return this.pictureId == other.pictureId; }

    public void setMatched(){ matched = true; }

    public int getPictureId(){ return pictureId; }

    public boolean isFaceUp(){ return faceUp; }

    public boolean isMatched(){ return matched; }
}