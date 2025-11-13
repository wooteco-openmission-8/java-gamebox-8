package gamebox.swing.components;

import gamebox.game_samepic.game.entity.Difficulty;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class DifficultySelectPanel extends JPanel {
    private final Consumer<Difficulty> onDifficultySelected;

    public DifficultySelectPanel(Consumer<Difficulty> onDifficultySelected) {
        this.onDifficultySelected = onDifficultySelected;
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        JLabel titleLabel = new JLabel("난이도를 선택하세요", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton easyButton = createDifficultyButton("쉬움 (4x4)", Difficulty.EASY);
        JButton mediumButton = createDifficultyButton("보통 (6x6)", Difficulty.MEDIUM);
        JButton hardButton = createDifficultyButton("어려움 (8x8)", Difficulty.HARD);

        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createDifficultyButton(String text, Difficulty difficulty) {
        JButton button = new JButton(text);
        button.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        button.setPreferredSize(new Dimension(200, 60));
        button.addActionListener(e -> {
            if (onDifficultySelected == null) {
                throw new IllegalStateException("[ERROR] 난이도 선택이 잘못됬습니다.");
            }
            onDifficultySelected.accept(difficulty);
        });
        return button;
    }
}