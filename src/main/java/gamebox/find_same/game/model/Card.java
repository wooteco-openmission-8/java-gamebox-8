package gamebox.find_same.game.model;

public class Card {
    private final int id;
    private final int pictureId;
    private final int row, col;
    private boolean faceUp;
    private boolean matched;

    public Card(int id, int pictureId, int row, int col) {
        this.id = id;
        this.pictureId = pictureId;
        this.row = row;
        this.col = col;
    }

    public void flip() {
        if (!matched) {
            faceUp = !faceUp;
        }
    }

    public boolean samePicture(Card other){ return this.pictureId == other.pictureId; }

    public void setMatched(){ matched = true; }

    public int getId(){ return id; }

    public int getPictureId(){ return pictureId; }

    public int getRow(){ return row; }

    public int getCol(){ return col; }

    public boolean isFaceUp(){ return faceUp; }

    public boolean isMatched(){ return matched; }
}