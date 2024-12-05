package com.moodmate.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class HomePage extends BaseHomePage {
    private static final int PADDING_X = 30; // Horizontal padding for fields
    private static final int FIELD_HEIGHT = 30; // Height for the input fields and button
    private static final int CORNER_RADIUS = 30; //
    private static final int MARGIN = 20; //
    private static final int MOTIVATION_HEIGHT = 80; //
    private static final int PART1_HEIGHT = 100; //
    private static final int PART2_HEIGHT = 450; //
//    private static final int PART3_HEIGHT = 300; 
    
    
    public HomePage() {
        super();

        int currentY = 20;
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Absolute positioning
        // Background label
        JLabel backgroundLabel = new JLabel(new ImageIcon("assets/images/background_homePage.png"));
        backgroundLabel.setBounds(0, 0, contentArea.getWidth(), contentArea.getHeight());
        contentPanel.add(backgroundLabel);
        backgroundLabel.setLayout(null);

        // Create container for motivational text with rounded corners
        JPanel motivationContainer = new JPanel();
        motivationContainer.setLayout(null);
        motivationContainer.setOpaque(false);
        motivationContainer.setBounds(PADDING_X, currentY, FRAME_WIDTH - 60, MOTIVATION_HEIGHT);

        JLabel motivationText = new JLabel(
                "<html>“Every day may not be good...<br>but there’s something good in every day.”</html>",
                SwingConstants.CENTER);
        motivationText.setFont(new Font(customFont, Font.ITALIC, 16));
        motivationText.setBounds(0, currentY, motivationContainer.getWidth() - 40, 40);
        motivationContainer.add(motivationText);

        backgroundLabel.add(motivationContainer);

        currentY += MOTIVATION_HEIGHT + MARGIN;

        // Create container for Part One
        JPanel partOneContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 200)); // Semi-transparent white
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS); // Rounded corners
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, CORNER_RADIUS, CORNER_RADIUS); // Border
            }
        };

        partOneContainer.setLayout(null);
        partOneContainer.setOpaque(false);
        partOneContainer.setBounds(PADDING_X, currentY, FRAME_WIDTH - 60, PART1_HEIGHT);

        JLabel titleLabel = new JLabel("Tell Me what you feel right now", SwingConstants.CENTER);
        titleLabel.setFont(new Font(customFont, Font.BOLD, 18));
        int titleWidth = partOneContainer.getWidth() - 40; // Width with some padding
        int titleHeight = FIELD_HEIGHT; // Fixed height for the label
        int titleX = (partOneContainer.getWidth() - titleWidth) / 2; // Center horizontally
        int titleY = (partOneContainer.getHeight() - FIELD_HEIGHT - MARGIN - FIELD_HEIGHT) / 2; // Center vertically
        titleLabel.setBounds(titleX, titleY, titleWidth, titleHeight);
        partOneContainer.add(titleLabel);

        JButton beginButton = new JButton("Begin");
        int buttonWidth = partOneContainer.getWidth() / 2; // Half the container's width
        int buttonHeight = FIELD_HEIGHT;
        int buttonX = (partOneContainer.getWidth() - buttonWidth) / 2; // Center horizontally
        int buttonY = FIELD_HEIGHT + MARGIN; // Position below the title
        beginButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
        beginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        beginButton.setBackground(customGreen);
        beginButton.setForeground(Color.WHITE);
        beginButton.setBorder(BorderFactory.createLineBorder(customGreen, 1, true));
        beginButton.setOpaque(true);
        beginButton.addActionListener(e -> {
            addToNavigationStack();
            new EFTPage();
            dispose();
        });
        partOneContainer.add(beginButton);

        currentY += PART1_HEIGHT + MARGIN;
        backgroundLabel.add(partOneContainer);
    

        // PART 2: Gallery-like page with tabs for real-time data (Daily, Weekly, Monthly)
        JPanel partTwoContainer = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 200)); // Semi-transparent white
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), CORNER_RADIUS, CORNER_RADIUS); // Rounded corners
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, CORNER_RADIUS, CORNER_RADIUS); // Border
            }
        };

        partTwoContainer.setLayout(new BorderLayout());
        partTwoContainer.setOpaque(false);
        partTwoContainer.setBounds(PADDING_X, currentY, FRAME_WIDTH - 60, PART2_HEIGHT);

        JLabel partTwoTitle = new JLabel("View Your Data", SwingConstants.CENTER);
        partTwoTitle.setFont(new Font(customFont, Font.BOLD, 16));
        partTwoTitle.setBorder(BorderFactory.createEmptyBorder(MARGIN - 20, 10, MARGIN - 20, 0));
        partTwoContainer.add(partTwoTitle, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font(customFont, Font.PLAIN, 14));

     // Example of fake data for five emotions (Joy, Sadness, Anger, Fear, Surprise)
        List<List<Integer>> dailyScores = new ArrayList<>();

        // Adding fake scores for each emotion
        dailyScores.add(List.of(70, 65, 60, 75, 80)); // Scores for Joy
        dailyScores.add(List.of(30, 40, 35, 20, 25)); // Scores for Sadness
        dailyScores.add(List.of(20, 25, 30, 15, 10)); // Scores for Anger
        dailyScores.add(List.of(40, 45, 50, 35, 30)); // Scores for Fear
        dailyScores.add(List.of(80, 75, 70, 85, 90)); // Scores for Surprise

        
        JPanel dailyTab = new JPanel(new BorderLayout());
        dailyTab.setBackground(Color.WHITE);
        dailyTab.add(createGraphPanel("daily", dailyScores), BorderLayout.CENTER);
        tabbedPane.addTab("Daily", dailyTab);


        // Weekly Tab
        JPanel weeklyTab = new JPanel(new BorderLayout());
        weeklyTab.setBackground(Color.WHITE);
        weeklyTab.add(createGraphPanel("weekly", dailyScores), BorderLayout.CENTER);
        tabbedPane.addTab("Weekly", weeklyTab);

        
       
        // Monthly Tab
        JPanel monthlyTab = new JPanel(new BorderLayout());
        monthlyTab.setBackground(Color.WHITE);
        monthlyTab.add(createGraphPanel("monthly",dailyScores ), BorderLayout.CENTER);
        tabbedPane.addTab("Monthly", monthlyTab);

        partTwoContainer.add(tabbedPane, BorderLayout.CENTER);
        backgroundLabel.add(partTwoContainer);

        currentY += PART2_HEIGHT + MARGIN;

 
        currentY += MARGIN;

    
        contentPanel.setPreferredSize(new Dimension(contentWidth, currentY + 100));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentArea.add(scrollPane, BorderLayout.CENTER);
    }
    
    private Component createGraphPanel(String timeframe, List<List<Integer>> scores) {
        JPanel graphPanel = new JPanel();
        graphPanel.setLayout(new BorderLayout());
        graphPanel.setOpaque(false);

        // Graph section
        JPanel chartArea = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int scoreCount = scores.get(0).size(); // Assuming all scores have the same size
                int[] time = new int[scoreCount];
                for (int i = 0; i < scoreCount; i++) {
                    time[i] = i + 1; // Create a time array dynamically
                }

                String[] emotions = {"Joy", "Sadness", "Anger", "Fear", "Surprise"};
                Color[] colors = {Color.YELLOW, Color.BLUE, Color.RED, Color.PINK, Color.GREEN};

                int width = getWidth(), height = getHeight() - 50, margin = 40;
                int graphWidth = width - 2 * margin, graphHeight = height - 2 * margin;

                g2.setColor(Color.BLACK);
                g2.drawLine(margin, height - margin, margin, margin);
                g2.drawLine(margin, height - margin, width - margin, height - margin);

                for (int i = 0; i < scores.size(); i++) {
                    g2.setColor(colors[i]);
                    for (int j = 0; j < time.length - 1; j++) {
                        int x1 = margin + (time[j] - 1) * graphWidth / (time.length - 1);
                        int y1 = height - margin - scores.get(i).get(j) * graphHeight / 100;
                        int x2 = margin + (time[j + 1] - 1) * graphWidth / (time.length - 1);
                        int y2 = height - margin - scores.get(i).get(j + 1) * graphHeight / 100;
                        g2.drawLine(x1, y1, x2, y2);
                    }
                }

                // Add labels for the emotions below the chart
                int labelY = height - margin + 40; // Position below the chart
                int labelXStart = margin; // Start at the margin
                int labelSpacing = (graphWidth - (emotions.length * 50)) / (emotions.length - 1); // Equal spacing

                g2.setFont(new Font(customFont, Font.PLAIN, 10));
                for (int i = 0; i < emotions.length; i++) {
                    int labelX = labelXStart + i * (50 + labelSpacing); // Position each label
                    g2.setColor(colors[i]); // Use the corresponding line color
                    g2.drawString(emotions[i], labelX, labelY);
                }
                g2.setColor(Color.BLACK); // Set to black for these labels
                g2.drawString("Time", width / 2, height - margin + 20);
                g2.drawString("Score", margin - 30, height / 2);
   
            }
        };

        chartArea.setPreferredSize(new Dimension(400, 200));
        graphPanel.add(chartArea, BorderLayout.CENTER);

        // Suggestions section
        JPanel suggestionsPanel = new JPanel();
        suggestionsPanel.setLayout(new BoxLayout(suggestionsPanel, BoxLayout.Y_AXIS));
        suggestionsPanel.setOpaque(false);

        JLabel suggestionsTitle = new JLabel("Suggestions");
        suggestionsTitle.setFont(new Font(customFont, Font.BOLD, 14));
        suggestionsPanel.add(suggestionsTitle);

        String[] suggestions = generateSuggestions(timeframe);
        for (String suggestion : suggestions) {
            JLabel suggestionLabel = new JLabel("• " + suggestion);
            suggestionLabel.setFont(new Font(customFont, Font.PLAIN, 12));
            suggestionsPanel.add(suggestionLabel);
        }

        graphPanel.add(suggestionsPanel, BorderLayout.SOUTH);
        return graphPanel;
    }



    private String[] generateSuggestions(String timeframe) {
        switch (timeframe.toLowerCase()) {
            case "daily":
                return new String[]{
                    "Take a 10-minute walk",
                    "Drink a glass of water",
                    "Write down 3 things you're grateful for"
                };
            case "weekly":
                return new String[]{
                    "Review your week's achievements",
                    "Plan your meals for the week",
                    "Schedule a call with a loved one"
                };
            case "monthly":
                return new String[]{
                    "Set your goals for the month",
                    "Declutter your workspace",
                    "Reflect on personal growth"
                };
            default:
                return new String[]{"No suggestions available"};
        }
    }



    public static void main(String[] args) {
        new HomePage();
    }
}
