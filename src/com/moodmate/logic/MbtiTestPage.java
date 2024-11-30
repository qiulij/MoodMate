package com.moodmate.logic;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.Hashtable;

public class MbtiTestPage extends BasePage {

    private static final int PADDING_X = 60; // Horizontal padding for fields
    private static final int FIELD_HEIGHT = 30; // Height for the input fields
    private static final int MARGIN = 20; // Vertical margin between components

    public MbtiTestPage() {
        super();

        // Create a contentPanel for all components
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null); // Absolute positioning

        // Add background image (it's just a visual element, so we add it to contentPanel)
        JLabel backgroundLabel = new JLabel(new ImageIcon("assets/images/background.png"));
        backgroundLabel.setBounds(0, 0, contentArea.getWidth(), contentArea.getHeight());
        contentPanel.add(backgroundLabel);
        backgroundLabel.setLayout(null);

        int currentY = 20; // Start Y position for components

        // Title Label
        JLabel titleLabel = new JLabel("Short MBTI Test", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        titleLabel.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT);
        contentPanel.add(titleLabel);

        currentY += FIELD_HEIGHT + MARGIN;


        // Create Sliders for MBTI preferences
        currentY = addMBTISlider(contentPanel, currentY, 
                "I feel energized when I’m in a ","group of people or social setting.");
        currentY = addMBTISlider(contentPanel, currentY, 
                "I often seek external activities or","discussions to recharge after a long day.");
        currentY = addMBTISlider(contentPanel, currentY, 
                "I prefer concrete facts and ","practical details over abstract ideas.");
        currentY = addMBTISlider(contentPanel, currentY, 
                "I trust my direct experiences ","more than my instincts or hunches.");
        currentY = addMBTISlider(contentPanel, currentY, 
                "I make decisions based on logic "," and objective analysis rather than emotions.");
        currentY = addMBTISlider(contentPanel, currentY,
                "When resolving conflicts, I focus","more on fairness and rules than on harmony and relationships.");
        currentY = addMBTISlider(contentPanel, currentY, 
                "I prefer planning and organization "," to flexibility and spontaneity.");
        currentY = addMBTISlider(contentPanel, currentY, 
                "I feel more comfortable making decisions ","early rather than keeping options open.");

        currentY += MARGIN;

        // next Button
        // next Button
        
        JButton nextButton = new JButton("Next");
        nextButton.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT + 10);

        nextButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 0, true));
//        signUpButton.setBackground(Color.decode("#45C78A"));
        nextButton.setBackground(customGreen);
        nextButton.setOpaque(true);
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));    
        
        nextButton.addActionListener(e -> {
        	
        	 addToNavigationStack();
        	 new NotificationSettingPage();
             dispose();
            ;
        });
 
        contentPanel.add(nextButton);
        currentY += MARGIN;
        contentPanel.setPreferredSize(new Dimension(contentArea.getWidth(), currentY + 100));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        contentArea.add(scrollPane, BorderLayout.CENTER);
    }

    private int addMBTISlider(JPanel contentPanel, int currentY, String question1, String question2) {
    	
    	JLabel questionLabel1 = new JLabel(question1);
    	questionLabel1.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
    	questionLabel1.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT);
    	questionLabel1.setBorder(BorderFactory.createMatteBorder(
    		    1, 0, 0, 0, // Top border only
    		    new Color(200, 200, 200) // Very light grey color
    		));
    	contentPanel.add(questionLabel1);

    	currentY += FIELD_HEIGHT;

    	JLabel questionLabel2 = new JLabel(question2);
    	questionLabel2.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
    	questionLabel2.setBounds(PADDING_X, currentY, contentArea.getWidth() - 2 * PADDING_X, FIELD_HEIGHT);
    	contentPanel.add(questionLabel2);

    	currentY += FIELD_HEIGHT;

    	
    	
        JSlider mbtiSlider = new JSlider(-5, 5, 0);  
        mbtiSlider.setBounds(PADDING_X -20, currentY, 300, FIELD_HEIGHT * 2); 
//        mbtiSlider.setMajorTickSpacing(2);
//        mbtiSlider.setMinorTickSpacing(1);

        // Create label table with updated font size
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        Font customFont = new Font("Helvetica Neue", Font.PLAIN, 9); // Customize the font and size

        JLabel labelStronglyDisagree = new JLabel("Strongly Disagree");
        labelStronglyDisagree.setFont(customFont);
        labels.put(-5, labelStronglyDisagree);

        JLabel labelNeutral = new JLabel("Neutral");
        labelNeutral.setFont(customFont);
        labels.put(0, labelNeutral);

        JLabel labelStronglyAgree = new JLabel("Strongly Agree");
        labelStronglyAgree.setFont(customFont);
        labels.put(5, labelStronglyAgree);

        // Apply the label table
        mbtiSlider.setLabelTable(labels);  
        mbtiSlider.setPaintLabels(true);

        // Add the slider to the content panel
        contentPanel.add(mbtiSlider);


        contentPanel.add(mbtiSlider);

        currentY += FIELD_HEIGHT + MARGIN*2;

        return currentY;
    }

    public static void main(String[] args) {
        new MbtiTestPage();
    }
}
