package Main.GUI;

import Main.Game.Character.Player;
import Main.GUI.Player.PlayerView;
import javax.swing.*;
import java.awt.*;

public class MainContainer extends JPanel {
    private PlayerView playerView;

    public MainContainer(Player player) {
        // Set up the main container layout
        setLayout(new BorderLayout());

        // Initialize the player view
        playerView = new PlayerView(player);

        // Add player view to center (will expand to fill available space)
        add(playerView, BorderLayout.CENTER);

        // Set background (will be visible if playerView is non-opaque)
        setBackground(Color.BLACK);

        // Set preferred size for the whole container
        setPreferredSize(new Dimension(800, 600));
    }

    public PlayerView getPlayerView() {
        return playerView;
    }
}