package gamebox.swing.components;

import gamebox.game_samepic.game.entity.Difficulty;
import gamebox.util.exceptions.ErrorType;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class DifficultySelectPanel extends JPanel {
    private static final String FONT = "맑은 고딕";
    private static final String SELECT_DIFFICULTY = "난이도를 선택하세요";
    private static final String EASY_BUTTON_TITLE = "쉬움 (4x4)";
    private static final String MEDIUM_BUTTON_TITLE = "보통 (6x6)";
    private static final String HARD_BUTTON_TITLE = "어려움 (8x8)";

    private static final int TITLE_FONT_SIZE = 24;
    private static final int ROW_COUNT = 3;
    private static final int COL_COUNT = 1;
    private static final int BUTTON_HORIZONTAL_GAP = 20;
    private static final int BUTTON_VERTICAL_GAP = 20;
    private static final int VERTICAL_PADDING = 50;
    private static final int HORIZONTAL_PADDING = 100;
    private static final int DIFFICULTY_BUTTON_FONT_SIZE = 18;
    private static final int DIFFICULTY_BUTTON_WIDTH = 200;
    private static final int DIFFICULTY_BUTTON_HEIGHT = 60;

    private final Consumer<Difficulty> onDifficultySelected;

    public DifficultySelectPanel(Consumer<Difficulty> onDifficultySelected) {
        this.onDifficultySelected = onDifficultySelected;
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        setTitle();
        setButtonPanel();
    }

    private void setTitle() {
        JLabel titleLabel = new JLabel(SELECT_DIFFICULTY, SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT, Font.BOLD, TITLE_FONT_SIZE));
        add(titleLabel, BorderLayout.NORTH);
    }

    private void setButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(
                ROW_COUNT, COL_COUNT, BUTTON_HORIZONTAL_GAP, BUTTON_VERTICAL_GAP
        ));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(
                VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING
        ));

        createButtons(buttonPanel);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void createButtons(JPanel buttonPanel) {
        JButton easyButton = createDifficultyButton(EASY_BUTTON_TITLE, Difficulty.EASY);
        JButton mediumButton = createDifficultyButton(MEDIUM_BUTTON_TITLE, Difficulty.MEDIUM);
        JButton hardButton = createDifficultyButton(HARD_BUTTON_TITLE, Difficulty.HARD);

        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);
    }

    private JButton createDifficultyButton(String text, Difficulty difficulty) {
        JButton button = new JButton(text);
        button.setFont(new Font(FONT, Font.PLAIN, DIFFICULTY_BUTTON_FONT_SIZE));
        button.setPreferredSize(new Dimension(DIFFICULTY_BUTTON_WIDTH, DIFFICULTY_BUTTON_HEIGHT));
        button.addActionListener(e -> {
            if (onDifficultySelected == null) {
                throw new IllegalStateException(ErrorType.INVALID_SELECTED_DIFFICULTY.getMessage());
            }
            onDifficultySelected.accept(difficulty);
        });
        return button;
    }
}