package gamebox.find_same.game.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {
    @Test
    @DisplayName("이미지 경로 테스트")
    void imagePathTest() {
        String path = "images/find_same/strawberry.png";

        URL url = Thread.currentThread().getContextClassLoader().getResource(path);

        assertNotNull(url, "[ERROR] 리소스를 찾을 수 없습니다. " + path);

        try {
            BufferedImage image = ImageIO.read(url);
            assertNotNull(image, "[ERROR] 이미지 읽기 실패: " + path);
            System.out.println("이미지 로드 성공: " + path);
        } catch (Exception e) {
            fail("[ERROR] 이미지 로드 중 예외 발생: " + e.getMessage());
        }
    }
}