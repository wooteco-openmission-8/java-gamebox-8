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

import java.util.Optional;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameSamePicPanel extends JPanel {
    private final List<ImageButton> imageButtons = new ArrayList<>();
    private final GameSamePicController controller;
    private final CardLayout cardLayout;
    private final JPanel containerPanel;

    public GameSamePicPanel() {
        PictureRepository repo = PictureRepository.getInstance();
        GameSamePicService service = new GameSamePicService(repo);
        this.controller = new GameSamePicController(service);

        controller.start();

        cardLayout = new CardLayout();
        containerPanel = new JPanel(cardLayout);

        setLayout(new BorderLayout());
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
        initializeGameUI();
        cardLayout.show(containerPanel, "GAME");
    }

    private void initializeGameUI() {
        JPanel gamePanel = new JPanel(new BorderLayout());
        imageButtons.clear();

        JPanel topPanel = new JPanel(new BorderLayout());

        JButton backButton = new JButton("뒤로 가기");
        backButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "난이도 선택 화면으로 돌아가시겠습니까?\n현재 게임이 초기화됩니다.",
                    "확인",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                showDifficultySelect();
            }
        });
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Game 같은 그림 찾기", SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        gamePanel.add(topPanel, BorderLayout.NORTH);

        GameSamePicBoard gameSamePicBoard = controller.getBoard();
        JPanel gridPanel = Grid.createGridPanel(gameSamePicBoard.getRows(), gameSamePicBoard.getCols());

        List<Card> cards = gameSamePicBoard.getCards();
        for (int i = 0; i < cards.size(); i++) {
            final int index = i;
            ImageButton btn = createImageButton(cards.get(i), index);
            imageButtons.add(btn);
            gridPanel.add(btn);
        }

        gamePanel.add(gridPanel, BorderLayout.CENTER);

        containerPanel.removeAll();
        containerPanel.add(gamePanel, "GAME");
        containerPanel.revalidate();
        containerPanel.repaint();
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

    private void handleCardClick(int index) {
        Optional<Boolean> result = controller.flip(index);

        if (result.isEmpty()) {
            updateCard(index);
            return;
        }

        updateCard(index);

        boolean isMatched = result.get();

        if (!isMatched) {
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

        if (controller.isGameOver()) {
            JOptionPane.showMessageDialog(this,
                    "게임 클리어!\n이동 횟수: " + controller.getMoves());
        }
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
        Card card = controller.getBoard().getCards().get(index);
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
}
