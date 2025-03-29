package Main.GUI;

import Main.GUI.Player.PlayerController;
import Main.Game.Character.Player;
import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create model
            Player player = new Player(400, 300);

            // Create main container with player view
            MainContainer mainContainer = new MainContainer(player);

            // Set up the frame
            JFrame frame = new JFrame("Player Movement Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(mainContainer);
            frame.pack();
            frame.setLocationRelativeTo(null);  // Center window
            frame.setVisible(true);

            // Set up controller
            PlayerController controller = new PlayerController(player);
            mainContainer.getPlayerView().addKeyListener(controller);
            mainContainer.getPlayerView().requestFocusInWindow();

            // Game loop
            new Timer(16, e -> {
                controller.update(0.016f);
                mainContainer.getPlayerView().repaint();
            }).start();
        });
    }
}