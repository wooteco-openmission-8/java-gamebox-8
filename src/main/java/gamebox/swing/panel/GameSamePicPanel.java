package gamebox.swing.panel;

import gamebox.game_samepic.game.controller.GameSamePicController;
import gamebox.game_samepic.game.entity.GameSamePicBoard;
import gamebox.game_samepic.game.entity.Card;
import gamebox.game_samepic.game.service.GameSamePicService;
import gamebox.game_samepic.picture.service.entity.Picture;
import gamebox.game_samepic.picture.service.repository.PictureRepository;
import gamebox.swing.components.DifficultySelectPanel;
import gamebox.game_samepic.game.entity.Difficulty;
import gamebox.swing.components.Grid;
import gamebox.swing.components.ImageButton;
import gamebox.swing.listener.GameListener;
import gamebox.swing.util.SwingUtils;

import java.util.Optional;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameSamePicPanel extends JPanel {
    private static final String GAME_CLEAR_MESSAGE = "게임 클리어!\n이동 횟수: ";
    private static final String GO_BACK_TO_SELECT_DIFFICULTY = "난이도 선택 화면으로 돌아가시겠습니까?\n현재 게임이 초기화됩니다.";
    private static final String YES = "확인";
    private static final String BACK_BUTTON_NAME = "뒤로가기";

    private final GameSamePicController controller;
    private final JPanel topPanel = new JPanel(new BorderLayout());
    private final JPanel gamePanel = new JPanel(new BorderLayout());
    private final JPanel containerPanel;
    private final List<ImageButton> imageButtons = new ArrayList<>();
    private final CardLayout cardLayout = new CardLayout();

    public GameSamePicPanel() {
        PictureRepository repo = PictureRepository.getInstance();
        GameSamePicService service = new GameSamePicService(repo);
        this.controller = new GameSamePicController(service);
        controller.start();

        setLayout(new BorderLayout());

        containerPanel = new JPanel(cardLayout);
        add(containerPanel, BorderLayout.CENTER);

        showDifficultySelect();
    }

    private void showDifficultySelect() {
        DifficultySelectPanel selectPanel = new DifficultySelectPanel(this::startGame);
        containerPanel.add(selectPanel, "SELECT");
        cardLayout.show(containerPanel, "SELECT");
    }

    private void startGame(Difficulty difficulty) {
        controller.start(difficulty);
        buildGameScreen();
        cardLayout.show(containerPanel, "GAME");
    }

    private void buildGameScreen() {
        imageButtons.clear();

        setTopPanel();
        drawBoard();

        containerPanel.removeAll();
        containerPanel.add(gamePanel, "GAME");
        SwingUtils.refresh(containerPanel);
    }

    private void handleCardClick(int index) {
        Optional<Boolean> result = controller.flip(index);

        if (result.isEmpty()) {
            updateCard(index);
            return;
        }

        updateCard(index);

        boolean isMatched = result.get();
        if (!isMatched) {
            resetMismatchedCards();
        }

        checkGameOver();
    }

    private void resetMismatchedCards() {
        List<Integer> flippedIndices = findFlippedCardIndices();

        Timer timer = new Timer(1000, ev -> {
            controller.getBoard().resetUnmatched();
            for (int i : flippedIndices) {
                updateCard(i);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private List<Integer> findFlippedCardIndices() {
        List<Integer> indices = new ArrayList<>();
        List<Card> cards = controller.getBoard().getCards();

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            if (card.isFaceUp() && !card.isMatched()) {
                indices.add(i);
            }
        }

        return indices;
    }

    private void updateCard(int index) {
        Card card = controller.getBoard().getCard(index);
        ImageButton btn = imageButtons.get(index);

        if (card.isFaceUp() || card.isMatched()) {
            showCardFront(btn, card, index);
        } else {
            showCardBack(btn, index);
        }

        if (card.isMatched()) {
            btn.setEnabled(false);
            btn.setDisabledIcon(btn.getIcon());
        }
    }

    private void showCardFront(ImageButton btn, Card card, int index) {
        String imagePath = (String) btn.getClientProperty("imagePath");
        try {
            btn.setIcon(btn.getImageIcon(imagePath));
            btn.setText("");
            btn.setBackground(Color.WHITE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void showCardBack(ImageButton btn, int index) {
        btn.setIcon(null);
        btn.setText("?");
        btn.setBackground(Color.LIGHT_GRAY);
    }

    private void setBackButton(JPanel topPanel) {
        JButton backButton = new JButton(BACK_BUTTON_NAME);
        backButton.addActionListener(
                new GameListener(
                        this,
                        GO_BACK_TO_SELECT_DIFFICULTY,
                        YES,
                        this::showDifficultySelect
                )
        );
        topPanel.add(backButton, BorderLayout.WEST);
    }

    private void setTopPanel() {
        setGameName(topPanel);
        setBackButton(topPanel);
        gamePanel.add(topPanel, BorderLayout.NORTH);
    }

    private void setGameName(JPanel topPanel) {
        JLabel titleLabel = new JLabel("Game 같은 그림 찾기", SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);
    }

    private void drawBoard() {
        GameSamePicBoard gameSamePicBoard = controller.getBoard();
        JPanel gridPanel = Grid.createGridPanel(gameSamePicBoard.getRows(), gameSamePicBoard.getCols());

        createPictureButtons(gameSamePicBoard, gridPanel);

        gamePanel.add(gridPanel, BorderLayout.CENTER);
    }

    private void createPictureButtons(GameSamePicBoard gameSamePicBoard, JPanel gridPanel) {
        List<Card> cards = gameSamePicBoard.getCards();

        for (int i = 0; i < cards.size(); i++) {
            final int index = i;
            ImageButton btn = createImageButton(cards.get(i), index);
            imageButtons.add(btn);
            gridPanel.add(btn);
        }
    }

    private ImageButton createImageButton(Card card, int index) {
        String pictureId = card.getPictureId();
        Picture picture = controller.getPicture(pictureId);
        ImageButton btn = new ImageButton(picture);

        btn.setPreferredSize(new Dimension(128, 128));
        btn.setText("?");
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setIcon(null);

        btn.addActionListener(e -> handleCardClick(index));

        return btn;
    }

    private void checkGameOver() {
        if (controller.isGameOver()) {
            JOptionPane.showMessageDialog(
                    this,
                    GAME_CLEAR_MESSAGE + controller.getMoves());
        }
    }
}
