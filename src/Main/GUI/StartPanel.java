package Main.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Starting panel showing the game's author and a Start button.
 */
public class StartPanel extends JPanel {
    private final JTextField nameInputField;
    public StartPanel(ActionListener startButtonListener) {
        setLayout(new GridBagLayout()); // Center everything nicely
        setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("The Last Survivor", SwingConstants.CENTER);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));

        JLabel authorLabel = new JLabel("Created by Sven Fischer", SwingConstants.CENTER);
        authorLabel.setForeground(Color.LIGHT_GRAY);
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        // New: Label for the input field
        JLabel nameLabel = new JLabel("Enter your nickname:", SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        // New: Simple text field for name input
        nameInputField = new JTextField(15); // 15 columns wide is a good default
        // Setting maximum size to prevent horizontal stretching by BoxLayout
        nameInputField.setMaximumSize(nameInputField.getPreferredSize());


        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(startButtonListener);

        // Panel to hold the labels and button vertically
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        // --- THE FIX IS HERE ---
        // Aligning components to the center of the Y-axis container (centerPanel)
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameInputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(authorLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(nameLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(nameInputField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Extra space before button

        centerPanel.add(startButton);

        // Use GridBagConstraints to center the centerPanel
        add(centerPanel, new GridBagConstraints());
    }
    // Inside your StartPanel class
    public String getPlayerName() {
        return nameInputField.getText().trim();
    }
}