package gamebox.swing.components;

import gamebox.game_samepic.picture.service.entity.Picture;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ImageButton extends JButton {
    private static final String IMAGE_PATH_KEY = "imagePath";
    private static final String IMAGE_ID_KEY = "imageId";

    private static final int IMAGE_SIZE = 128;

    private Picture picture;

    public ImageButton(Picture picture) {
        this.picture = picture;
        initButton();
    }

    public ImageButton() {}

    private void initButton() {
        if (picture != null) {
            putClientProperty(IMAGE_ID_KEY, picture.getId());
            putClientProperty(IMAGE_PATH_KEY, picture.getPath());
            setBorderPainted(false);
            setContentAreaFilled(true);
            setFocusPainted(false);
            setOpaque(true);
        }
    }

    public JButton getButton() {
        return this;
    }

    public ImageIcon getImageIcon(String imagePath) throws IOException {
        URL resource = ImageButton.class.getResource(imagePath);
        if (resource == null) {
            return new ImageIcon("");
        }

        Image image = scaleImage(ImageIO.read(resource), IMAGE_SIZE, IMAGE_SIZE);

        return new ImageIcon(image);
    }

    public Image scaleImage(Image image, int newWidth, int newHeight) {
        return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }
}