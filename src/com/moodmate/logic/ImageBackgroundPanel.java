package com.moodmate.logic;

import javax.swing.*;
import java.awt.*;

class ImageBackgroundPanel extends JPanel {
    private Image backgroundImage;

    public ImageBackgroundPanel(String imagePath) {
        try {
            backgroundImage = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
