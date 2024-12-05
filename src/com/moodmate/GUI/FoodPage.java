package com.moodmate.GUI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.Hashtable;

public class FoodPage extends BaseHomePage {

    private static final int PADDING_X = 30; // Horizontal padding for fields
    private static final int FIELD_HEIGHT = 30; // Height for the input fields
    private static final int PIC_SIZE = 100; // Height for the input fields   
    private static final int MARGIN = 20; // Vertical margin between components

    int contentWidth = contentArea.getWidth();

    public FoodPage() {
        super();

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Absolute positioning

        int currentY = 20; // Start Y position for components

        // Title Label
        JLabel titleLabel = new JLabel("Monitor Your Nutrition", SwingConstants.CENTER);
        titleLabel.setFont(new Font(customFont, Font.BOLD, 20));
        titleLabel.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(titleLabel);

        currentY += FIELD_HEIGHT + MARGIN;

     // Appetite Question with Radio Buttons
        JLabel appetiteLabel = new JLabel("How has your appetite been?", SwingConstants.LEFT);
        appetiteLabel.setFont(new Font(customFont, Font.PLAIN, 16));
        appetiteLabel.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(appetiteLabel);

        currentY += FIELD_HEIGHT;

        // Create a transparent panel for the radio buttons
        JPanel appetitePanel = new JPanel();
        appetitePanel.setLayout(new GridLayout(7, 1, 5, 5)); // 7 options, vertical layout
        appetitePanel.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, FIELD_HEIGHT * 7);
        appetitePanel.setOpaque(false); // No background for the panel

        ButtonGroup appetiteGroup = new ButtonGroup(); // Group for radio buttons

        String[] appetiteOptions = {
            "I have no appetite at all.",
            "My appetite is much less than before.",
            "My appetite is somewhat less than usual.",
            "I have not experienced any change in my appetite.",
            "My appetite is somewhat greater than usual.",
            "My appetite is much greater than usual.",
            "I crave food all the time."
        };

        for (String option : appetiteOptions) {
            JRadioButton radioButton = new JRadioButton(option);
            radioButton.setFont(new Font(customFont, Font.PLAIN, 14));
            radioButton.setOpaque(false); // No background for the button
            appetiteGroup.add(radioButton);
            appetitePanel.add(radioButton);
        }

        // Add the panel to the content panel
        contentPanel.add(appetitePanel);

        currentY += FIELD_HEIGHT * 7 + MARGIN;
        // Title Label
        JLabel title2Label = new JLabel("Monitor Your Nutrition", SwingConstants.CENTER);
        title2Label.setFont(new Font(customFont, Font.BOLD, 20));
        title2Label.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(title2Label);

        currentY += FIELD_HEIGHT + MARGIN;


        // Macronutrient Sliders
 
     

        String[] categories = {"Carbs", "Protein", "Fat", "Minerals", "Vitamins", "Water"};
        String[] icons = {"carbs.png", "protein.png", "fat.png", "minerals.png", "vitamins.png", "water.png"};

      
        for (int i = 0; i < categories.length; i++) {
            // Container Panel
            JPanel container = new JPanel();
            container.setLayout(null);
            container.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, PIC_SIZE + FIELD_HEIGHT + 10);

            // Icon Label;
            String path =  "assets/images/foodIcons/" + icons[i];
            JLabel iconLabel = new JLabel(new ImageIcon(path));
            iconLabel.setBounds(0, 0, PIC_SIZE, PIC_SIZE);
            container.add(iconLabel);

            // Slider Title
            JLabel sliderTitle = new JLabel(categories[i]);
            sliderTitle.setFont(new Font(customFont, Font.BOLD, 16));
            sliderTitle.setBounds(PIC_SIZE + 10, 0, contentWidth - PIC_SIZE - PADDING_X * 2, FIELD_HEIGHT);
            container.add(sliderTitle);

            // Slider
            JSlider slider = new JSlider(0, 100);
            slider.setBounds(PIC_SIZE + 10, FIELD_HEIGHT , contentWidth - PIC_SIZE - PADDING_X * 3, FIELD_HEIGHT * 3);
            slider.setMajorTickSpacing(20);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);

            // Custom labels for the slider
           
            Hashtable<Integer, JLabel> labelTable = new Hashtable<>();

            labelTable.put(0, new JLabel("0"));
            labelTable.put(50, new JLabel("50"));
            labelTable.put(100, new JLabel("100"));

            slider.setLabelTable(labelTable);

            container.add(slider);

            // Add container to content panel
            contentPanel.add(container);

            currentY += PIC_SIZE + FIELD_HEIGHT + MARGIN;
        }


        currentY += MARGIN;

        // Next Button
        JButton nextButton = new JButton("Next");
        nextButton.setBounds(PADDING_X, currentY, contentWidth - 2 * PADDING_X, FIELD_HEIGHT + 10);
        nextButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0, true));
        nextButton.setBackground(customGreen);
        nextButton.setOpaque(true);
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        nextButton.addActionListener(e -> {
            addToNavigationStack();
            new RealTimeSuggestionPage();
            dispose();
        });

        contentPanel.add(nextButton);
        currentY += FIELD_HEIGHT + MARGIN;

        contentPanel.setPreferredSize(new Dimension(contentWidth, currentY + 100));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentArea.add(scrollPane, BorderLayout.CENTER);
    }

   

    public static void main(String[] args) {
        new FoodPage();
    }
}
