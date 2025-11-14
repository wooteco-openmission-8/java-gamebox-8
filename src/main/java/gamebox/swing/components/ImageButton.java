package gamebox.swing.components;

import gamebox.game_samepic.picture.service.entity.Picture;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class ImageButton extends JButton {
    private Picture picture;

    public ImageButton(Picture picture) {
        this.picture = picture;
        initButton();
    }

    public ImageButton() {}

    private void initButton() {
        if (picture != null) {
            putClientProperty("imageId", picture.getId());
            putClientProperty("imagePath", picture.getPath());
            putClientProperty("imageGroup", picture.getGroup());
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

        Image image = scaleImage(ImageIO.read(resource), 128, 128);

        return new ImageIcon(image);
    }

    public Image scaleImage(Image image, int newWidth, int newHeight) {
        return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }
}