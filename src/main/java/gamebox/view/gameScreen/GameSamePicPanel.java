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
    public GameSamePicPanel() {
        setOpaque(true);
        setLayout(new BorderLayout());
        add(new JLabel("Game 같은 그림 찾기", SwingConstants.CENTER), BorderLayout.CENTER);

        JPanel gridPanel = Grid.createGridPanel(5, 5);

        // 테스트용 버튼 추가
        for (int i = 0; i < 5 * 5; i++) {
            int id = i;
            //picture는 백엔드에서 받아와야함 임시로 인터페이스 구현
            ImageButton btn = new ImageButton(new Picture() {
                @Override
                public Integer getId() {
                    return id;
                }

                @Override
                public String getPicturePath() {
                    return "/images/find_same/strawberry.png"; //테스트용, 이후 동적으로 변경
                }
            }).getButton();



            btn.setPreferredSize(new Dimension(80, 80));
            btn.addActionListener(e -> {
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

                if (buttons.size() >= 2) {
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
                    });
                    timer.setRepeats(false); // 한 번만 실행
                    timer.start();
                }
            });
            gridPanel.add(btn);
        }

        add(gridPanel, BorderLayout.CENTER);
    }
}
