package gamebox.game2048;

import java.awt.Color;

public enum TileValue {
    EMPTY(0, Color.DARK_GRAY, Color.BLACK),
    TWO(2, Color.LIGHT_GRAY, Color.BLACK),
    FOUR(4, Color.GRAY, Color.BLACK),
    EIGHT(8, Color.ORANGE, Color.WHITE),
    SIXTEEN(16, Color.RED, Color.WHITE),
    THIRTY_TWO(32, Color.MAGENTA, Color.WHITE),
    SIXTY_FOUR(64, Color.YELLOW, Color.WHITE),
    ONE_TWENTY_EIGHT(128, Color.GREEN, Color.WHITE),
    TWO_FIFTY_SIX(256, Color.CYAN, Color.WHITE),
    FIVE_TWELVE(512, Color.BLUE, Color.WHITE),
    ONE_ZERO_TWO_FOUR(1024, Color.PINK, Color.WHITE),
    TWO_ZERO_FOUR_EIGHT(2048, Color.WHITE, Color.BLACK);

    private final int value;
    private final Color background;
    private final Color textColor;

    TileValue(int value, Color background, Color textColor) {
        this.value = value;
        this.background = background;
        this.textColor = textColor;
    }

    public Color getBackground() {
        return background;
    }

    public Color getTextColor() {
        return textColor;
    }

    public static TileValue fromValue(int value) {
        for (TileValue tv : values()) {
            if (tv.value == value) {
                return tv;
            }
        }
        return Default();
    }

    public static TileValue Default() {
        return fromValue(0);
    }
}
