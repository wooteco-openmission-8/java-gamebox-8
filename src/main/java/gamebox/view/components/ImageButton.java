package gamebox.view.components;

import gamebox.view.gameScreen.Picture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ImageButton extends JButton {
    private Picture picture;
    public ImageButton(Picture picture) {
        this.picture = picture;
    }

    public ImageButton() {}

    public ImageButton getButton() {
        try {
            return createImageButton(picture.getPicturePath(), picture.getId());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageButton createImageButton(String imagePath, int imageId) throws IOException {
        ImageIcon icon = getImageIcon(imagePath);

        // 버튼 생성
        ImageButton button = new ImageButton();

        button.putClientProperty("imageId", imageId);
        button.putClientProperty("imagePath", imagePath);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setIcon(icon);

        return button;
    }

    public ImageIcon getImageIcon(String imagePath) throws IOException {
        URL resource = ImageButton.class.getResource(imagePath);
        if (resource == null) {
            return new ImageIcon("");
        }

        Image image = scaleImage(ImageIO.read(resource), 128, 128);

        return new ImageIcon(image);
    }

    public Image scaleImage(Image image, int newWidth, int newHeight) {
        return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }
}
