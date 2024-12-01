package com.moodmate.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HobbiesPage extends BasePage {

    private static final int PADDING_X = 40; // Horizontal padding for fields
    private static final int FIELD_HEIGHT = 30; // Height for the input fields
    private static final int MARGIN = 20; // Vertical margin between components
    private static final int BUTTON_SIZE = 200; // Button size (square dimensions)

    // Array of hobby image filenames
    private static final String[] HOBBY_IMAGES = {
        "art.png", "collection.png", "cooking.png", "diy.png", 
        "entertainment.png", "game.png", "outdoors.png", "performance.png", 
        "relax.png", "social.png", "sports.png", "travel.png"
    };

    // List to keep track of selected hobbies
    private final ArrayList<String> selectedHobbies = new ArrayList<>();

    public HobbiesPage() {
        super();

        // Create a contentPanel for all components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Absolute positioning
        contentPanel.setBackground(customBackgroundColor);

        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, contentArea.getWidth(), contentArea.getHeight());
        contentPanel.add(backgroundLabel);
        backgroundLabel.setLayout(null);

        int currentY = 20; // Start Y position for components

        // Title Label
        JLabel titleLabel = new JLabel("Choose Hobbies", SwingConstants.CENTER);
        titleLabel.setFont(new Font(customFont, Font.BOLD, 20));
        titleLabel.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(titleLabel);

        currentY += FIELD_HEIGHT + MARGIN;

        // Panel for hobby options
        JPanel hobbiesPanel = new JPanel();
        hobbiesPanel.setLayout(new GridLayout(4, 3, 10, 10)); // 4 rows, 3 columns, with gaps
        hobbiesPanel.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, 2 * BUTTON_SIZE + 30);
        hobbiesPanel.setBackground(customBackgroundColor);

        // Add image buttons for hobbies
        ArrayList<JButton> hobbyButtons = new ArrayList<>();
        for (String imageName : HOBBY_IMAGES) {
            // Load the pre-resized image as an ImageIcon
            ImageIcon icon = new ImageIcon("assets/images/hobby/" + imageName);
            JButton button = new JButton(icon); // Use the resized image directly

            button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0)); // Default border
            button.setContentAreaFilled(false); // Transparent background
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Add toggle effect for red border when selected
            button.addActionListener(e -> {
                // Toggle circular red border
                button.repaint(); // Repaint the button to update border drawing
                button.setContentAreaFilled(false); // Keeps the circular background consistent
                button.setOpaque(false); // Makes sure background respects transparency

                String hobbyName = imageName.replace(".png", ""); // Extract hobby name
                if (button.getClientProperty("selected") != Boolean.TRUE) {
                    button.putClientProperty("selected", Boolean.TRUE);
                    selectedHobbies.add(hobbyName); // Add hobby to selected list
                } else {
                    button.putClientProperty("selected", Boolean.FALSE);
                    selectedHobbies.remove(hobbyName); // Remove hobby from selected list
                }
            });

            // Custom painting to draw a circular border
            button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
                @Override
                public void paint(Graphics g, JComponent c) {
                    super.paint(g, c);
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    if (button.getClientProperty("selected") == Boolean.TRUE) {
                        g2.setColor(customGreen);
                        int padding = 1; // Padding for circular border
                        g2.setStroke(new BasicStroke(3)); // Thickness of the border
                        g2.drawOval(padding, padding, c.getWidth() - 2 * padding, c.getHeight() - 2 * padding);
                    }
                }
            });

            hobbiesPanel.add(button);
            hobbyButtons.add(button);
            currentY += MARGIN ;
        }
        contentPanel.add(hobbiesPanel);
        currentY += MARGIN + BUTTON_SIZE;

        // Next Button
        JButton nextButton = new JButton("Next");
        nextButton.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT + 10);

        nextButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0, true));
        nextButton.setBackground(customGreen);
        nextButton.setOpaque(true);
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        nextButton.addActionListener(e -> {
            if (selectedHobbies.size() < 2) {
                // Display a warning message if fewer than 2 hobbies are selected
                JOptionPane.showMessageDialog(
                    this,
                    "Please select at least two hobbies before proceeding.",
                    "Selection Required",
                    JOptionPane.WARNING_MESSAGE
                );
            } else {
                // Proceed to the next page
                addToNavigationStack();
                new NotificationSettingPage();
                dispose();
            }
        });

        contentPanel.add(nextButton);
        currentY += FIELD_HEIGHT + MARGIN;

        contentPanel.setPreferredSize(new Dimension(contentArea.getWidth(), currentY + 100));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentArea.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new HobbiesPage();
    }
}
