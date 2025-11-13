package gamebox.view.gameScreen;

import gamebox.view.components.Grid;
import gamebox.view.components.ImageButton;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameSamePicPanel extends JPanel {
    private final List<ImageButton> buttons = new ArrayList<>();
    private boolean isLocked = false; // 버튼 안 눌리게 관리.

    public GameSamePicPanel() {
        setOpaque(true);
        setLayout(new BorderLayout());
        add(new JLabel("Game 같은 그림 찾기", SwingConstants.CENTER), BorderLayout.CENTER);

        JPanel gridPanel = Grid.createGridPanel(4, 4);

        // 테스트용 버튼 추가
        for (int i = 0; i < 4 * 4; i++) {
            int id = i;
            //picture는 백엔드에서 받아와야함 임시로 인터페이스 구현
            ImageButton btn = new ImageButton(new Picture() {
                @Override
                public Integer getId() {
                    return id;
                }

                @Override
                public String getPicturePath() {
                    return "/images/find_same/pic" + (id + 1) + ".png"; //테스트용, 이후 동적으로 변경
                }
            }).getButton();

            btn.setPreferredSize(new Dimension(80, 80));

            btn.addActionListener(e -> {
                if (isLocked) {
                    return;
                }

                Object src = e.getSource();
                if (src instanceof JComponent component) {
                    Object imageId = component.getClientProperty("imageId");
                    System.out.println("클릭된 이미지 ID: " + imageId);
                }

                if (btn.getIcon() == null) {
                    return;
                }
                buttons.add(btn);
                btn.setIcon(null);

                if (buttons.size() == 2) {
                    isLocked = true;

                    Timer timer = new Timer(1000, ev -> { // 1000ms = 1초 딜레이
                        buttons.forEach(button -> {
                            String imagePath = button.getClientProperty("imagePath").toString();
                            try {
                                button.setIcon(button.getImageIcon(imagePath));
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        });
                        buttons.clear();
                        isLocked = false;
                    });
                    timer.setRepeats(false); // 한 번만 실행
                    timer.start();
                }
            });
            gridPanel.add(btn);
        }

        add(gridPanel, BorderLayout.CENTER);
    }

    private static String pathFor(int id) {
        int n = (id % 5) + 1;
        return "images/find_same/pic" + n + ".png";
    }
}
