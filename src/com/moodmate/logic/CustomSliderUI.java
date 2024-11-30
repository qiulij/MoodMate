package com.moodmate.logic;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import javax.swing.event.*;

public class CustomSliderUI extends BasicSliderUI {

    private ImageIcon thumbIcon;  // Store the custom thumb icon

    public CustomSliderUI(JSlider slider, ImageIcon thumbIcon) {
        super(slider);
        this.thumbIcon = thumbIcon;  // Initialize with the custom thumb icon
    }

    // Override the paintThumb method to paint the custom thumb
    @Override
    public void paintThumb(Graphics g) {
        if (thumbIcon != null) {
            int thumbX = getThumbRect().x;  // Get the x position of the thumb
            int thumbY = getThumbRect().y;  // Get the y position of the thumb
            thumbIcon.paintIcon(slider, g, thumbX, thumbY);  // Draw the custom thumb icon
        } else {
            super.paintThumb(g);  // Default thumb drawing if no custom icon
        }
    }
}
