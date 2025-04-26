package Main.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Starting panel showing the game's author and a Start button.
 */
public class StartPanel extends JPanel {

    public StartPanel(ActionListener startButtonListener) {
        setLayout(new GridBagLayout()); // Center everything nicely
        setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("The Last Survivor", SwingConstants.CENTER);
        titleLabel.setForeground(Color.RED);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));

        JLabel authorLabel = new JLabel("Created Sven Fischer", SwingConstants.CENTER);
        authorLabel.setForeground(Color.LIGHT_GRAY);
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(startButtonListener);

        // Panel to hold the labels and button vertically
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(titleLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(authorLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        centerPanel.add(startButton);

        // Use GridBagConstraints to center the centerPanel
        add(centerPanel, new GridBagConstraints());
    }
}
